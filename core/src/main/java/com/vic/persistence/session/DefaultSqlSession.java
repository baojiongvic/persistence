package com.vic.persistence.session;

import com.vic.persistence.config.Configuration;

import java.util.List;
import java.util.Objects;

/**
 * @author vic
 * @date 2021-11-22 22:34:01
 **/
public class DefaultSqlSession implements SqlSession {

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
}
