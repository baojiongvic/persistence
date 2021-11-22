package com.vic.persistence.io;

import java.io.InputStream;

/**
 * @author vic
 * @date 2021-11-21 21:41:01
 **/
public class Resource {

    public static InputStream getResourceAsStream(String path) {
        return Resource.class.getClassLoader().getResourceAsStream(path);
    }
}
