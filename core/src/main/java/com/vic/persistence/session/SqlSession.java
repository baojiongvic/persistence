package com.vic.persistence.session;

import java.util.List;

/**
 * @author vic
 * @date 2021-11-22 22:34:16
 **/
public interface SqlSession {

    /**
     * 查询列表
     *
     * @param statementId 语句id
     * @param params      参数个数
     * @return {@link List}<{@link E}>
     * @throws Exception 异常
     */
    <E> List<E> selectList(String statementId, Object... params) throws Exception;

    /**
     * 查询一个
     *
     * @param statementId 语句id
     * @param params      参数个数
     * @return {@link T}
     * @throws Exception 异常
     */
    <T> T selectOne(String statementId, Object... params) throws Exception;
}
