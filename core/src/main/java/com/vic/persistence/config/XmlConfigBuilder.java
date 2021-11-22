package com.vic.persistence.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.resourcepool.ResourcePool;
import com.vic.persistence.io.Resource;
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

    private final Configuration configuration;

    public XmlConfigBuilder() {
        this.configuration = new Configuration();
    }

    public Configuration parse(InputStream is) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(is);

        // 解析sqlMapConfig.xml
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
        configuration.setDataSource(dataSource);

        // 解析mapper.xml
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element mapper : mapperList) {
            String resource = mapper.attributeValue("resource");
            InputStream inputStream = Resource.getResourceAsStream(resource);
            XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(configuration);
            xmlMapperBuilder.parse(inputStream);
        }
        return configuration;
    }
}
