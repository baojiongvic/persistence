package com.vic.persistence.config;

import com.vic.persistence.mapper.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author vic
 * @date 2021-11-22 22:34:29
 **/
public class XmlMapperBuilder {

    private final Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream is) throws DocumentException {
        Document document = new SAXReader().read(is);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");

        List<Element> list = rootElement.selectNodes("//select");
        list.forEach(element -> {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String parameterType = element.attributeValue("parameterType");
            String sqlText = element.getText();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sqlText);

            configuration.getMappedStatementMap().put(namespace + "." + id, mappedStatement);
        });
    }
}
