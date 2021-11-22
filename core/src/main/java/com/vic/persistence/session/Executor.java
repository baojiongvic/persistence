package com.vic.persistence.session;

import com.vic.persistence.config.Configuration;
import com.vic.persistence.mapper.MappedStatement;

import java.util.List;

/**
 * @author vic
 * @date 2021-11-22 22:42:22
 **/
public interface Executor {

    /**
     * 查询
     *
     * @param configuration   配置
     * @param mappedStatement 映射语句
     * @param params          参数个数
     * @return {@link List}<{@link E}>
     * @throws Exception 异常
     */
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;
}
