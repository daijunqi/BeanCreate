package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * Created by dai on 16-9-11.
 */
public class DBBase extends Constant {

    public static Connection connection;


    /**
     * 打开数据库连接
     */
    public static void getConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭数据库连接
     */
    public static void close() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 运行sql
     *
     * @param sql 需要运行的sql
     * @return
     */
    public static ResultSet runSql(String sql) {
        ResultSet set = null;
        try {
            java.sql.Statement statement = connection.createStatement();
            set = statement.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }
}
