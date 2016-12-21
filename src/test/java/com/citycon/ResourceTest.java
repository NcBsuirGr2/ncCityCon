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
        //properties.load(cl.getResourceAsStream("/database.properties"));
        File file = new File("./");
        Logger logger = LoggerFactory.getLogger("testing");
        for (File f : file.listFiles()) {
            logger.debug(f.getAbsolutePath());
        }
        //assertEquals(properties.get("username"), "root");
    }
}
