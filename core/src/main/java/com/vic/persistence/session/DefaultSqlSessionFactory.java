package com.vic.persistence.session;

import com.vic.persistence.config.Configuration;

/**
 * @author vic
 * @date 2021-11-22 22:25:53
 **/
public class DefaultSqlSessionFactory implements SqlSessionFactory{

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
