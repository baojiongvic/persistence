package com.vic.persistence.config;

import com.vic.persistence.mapper.MappedStatement;
import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vic
 * @date 2021-11-21 21:08:28
 **/
@Data
public class Configuration {

    private DataSource dataSource;

    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

}
