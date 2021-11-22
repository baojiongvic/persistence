package com.vic.persistence.session;

/**
 * @author vic
 * @date 2021-11-21 21:40:44
 **/
public interface SqlSessionFactory {

    /**
     * 创建SqlSession
     *
     * @return {@link SqlSession}
     */
    SqlSession openSession();
}
