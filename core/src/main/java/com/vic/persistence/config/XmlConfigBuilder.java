package com.vic.persistence.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author vic
 * @date 2021-11-21 21:40:14
 **/
public class XmlConfigBuilder {

    public Configuration parse(InputStream is) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(is);
        Element rootElement = document.getRootElement();
        List<Element> list = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        list.forEach(element -> {
            properties.setProperty(element.attributeValue("name"), element.attributeValue("value"));
        });
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(properties.getProperty("driverClass"));
        dataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        dataSource.setUser(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
        Configuration configuration = new Configuration();
        configuration.setDataSource(dataSource);
        return configuration;
    }
}
