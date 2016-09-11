package com;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by dai on 16-9-11.
 */
public class Constant {

    protected static String username;

    protected static String password;

    protected static String url;

    protected static String packageRoot;

    protected static String targetFileRoot;

    static {
        InputStream stream = Constant.class.getResourceAsStream("/config/config.properties");
        Properties prop = new Properties();
        try {
            prop.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        username = prop.getProperty("username");
        password = prop.getProperty("password");
        url = prop.getProperty("url");
        packageRoot = prop.getProperty("packageRoot");
        targetFileRoot = prop.getProperty("targetFileRoot");
    }

}
