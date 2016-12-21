package com.citycon;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Properties;


import static org.junit.Assert.assertEquals;

public class ResourceTest {
    @Test
    public void TestResource() throws IOException{
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream("/registration.properties"));
        assertEquals(properties.get("username"), "cityconteam@gmail.com");
    }
}
