package com.vic.persistence.session;

import com.vic.persistence.config.Configuration;
import com.vic.persistence.config.XmlConfigBuilder;

import java.io.InputStream;

/**
 * @author vic
 * @date 2021-11-21 21:40:31
 **/
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream is) throws Exception {
        // 1. 使用dom4j解析配置文件 将解析出内容封装为Configuration对象
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parse(is);

        // 2. 创建sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return sqlSessionFactory;
    }
}
