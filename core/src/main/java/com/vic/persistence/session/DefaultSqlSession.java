package com.vic.persistence.session;

import com.vic.persistence.config.Configuration;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author vic
 * @date 2021-11-22 22:34:01
 **/
public class DefaultSqlSession implements SqlSession {

    public static final String STATEMENT_ID = "%s.%s";

    private final Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        SimpleExecutor executor = new SimpleExecutor();
        return executor.query(configuration, configuration.getMappedStatementMap().get(statementId), params);
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<T> list = selectList(statementId, params);
        if (list != null && list.size() == 1) {
            return list.get(0);
        } else {
            throw new RuntimeException("查询结果为空或者大于1");
        }
    }

    @Override
    public <T> T getMapper(Class<?> clazz) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clazz},
                (proxy, method, args) -> {
            String statementId = String.format(STATEMENT_ID, method.getDeclaringClass().getName(), method.getName());
            // 根据返回类型是不是泛型参数化判断返回结果是多条还是单条
            if (method.getGenericReturnType() instanceof ParameterizedType) {
                return selectList(statementId, args);
            }
            return selectOne(statementId, args);
        });
    }
}
