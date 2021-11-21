package com.vic.persistence.mapper;

import lombok.Data;

/**
 * @author vic
 * @date 2021-11-21 21:19:15
 **/
@Data
public class MappedStatement {

    private String id;

    private String parameterType;

    private String resultType;

    private String sql;
}
