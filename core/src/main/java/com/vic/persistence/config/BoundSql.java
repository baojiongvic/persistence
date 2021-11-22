package com.vic.persistence.config;

import com.vic.persistence.utils.ParameterMapping;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vic
 * @date 2021/11/22 10:54 下午
 **/
@Data
public class BoundSql {

    private String sql;

    private List<ParameterMapping> parameterMappings = new ArrayList<>();

    public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }
}
