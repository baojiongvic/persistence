package com.vic.persistence.session;

import com.vic.persistence.config.BoundSql;
import com.vic.persistence.config.Configuration;
import com.vic.persistence.mapper.MappedStatement;
import com.vic.persistence.utils.GenericTokenParser;
import com.vic.persistence.utils.ParameterMapping;
import com.vic.persistence.utils.ParameterMappingTokenHandler;
import com.vic.persistence.utils.TokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author vic
 * @date 2021-11-22 22:44:18
 **/
public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        // 1. 获取数据库连接
        Connection connection = configuration.getDataSource().getConnection();

        // 2. 解析sql语句 替换占位符
        BoundSql boundSql = getBoundSql(mappedStatement.getSql());

        // 3. 获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());

        Class<?> parameterTypeClass = getClassType(mappedStatement.getParameterType());
        // 4. 设置参数
        for (int i = 0; i < boundSql.getParameterMappings().size(); i++) {
            ParameterMapping parameterMapping = boundSql.getParameterMappings().get(i);
            String content = parameterMapping.getContent();

            // 使用反射根据字段名获取值
            Field field = parameterTypeClass.getDeclaredField(content);
            field.setAccessible(true);

            preparedStatement.setObject(i + 1, field.get(params[0]));
        }

        // 5. 执行sql
        ResultSet resultSet = preparedStatement.executeQuery();

        Class<?> resultType = getClassType(mappedStatement.getResultType());
        List<Object> resultList = new ArrayList<>();
        // 封装返回对象
        while (resultSet.next()) {
            Object o = resultType.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(columnName);

                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultType);
                propertyDescriptor.getWriteMethod().invoke(o, value);
            }
            resultList.add(o);
        }

        return (List<E>) resultList;
    }

    private Class<?> getClassType(String className) throws ClassNotFoundException {
        if (className != null) {
            return Class.forName(className);
        }
        return null;
    }

    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser parser = new GenericTokenParser("#{", "}", tokenHandler);
        String parseSql = parser.parse(sql);
        List<ParameterMapping> parameterMappings = tokenHandler.getParameterMappings();
        return new BoundSql(parseSql, parameterMappings);
    }
}
