package com.citycon;

import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class ResourceTest {
    @Test
    public void TestResource() throws IOException{
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        Properties properties = new Properties();
        properties.load(cl.getResourceAsStream("database.properties"));
        assertEquals(properties.get("username"), "root");
    }
}
