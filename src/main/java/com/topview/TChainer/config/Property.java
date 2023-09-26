package com.topview.TChainer.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author 刘家辉
 * @date 2023/09/25
 */
public class Property  {
    private static final String CONFIG_FILE = "config.properties";
    private static Properties properties;

    static {
        try {
            properties = new Properties();
            properties.load(Files.newInputStream(Paths.get(CONFIG_FILE)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key){
          return properties.getProperty(key);
    }

}
