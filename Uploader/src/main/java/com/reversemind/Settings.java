package com.reversemind;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Date: 5/15/13
 * Time: 10:27 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class Settings implements Serializable {

    public static final String JDBC_HOST   = "localhost";
    public static final int    JDBC_PORT   = 5432;
    public static final String JDBC_DB     = "twitter";

    public static final String DB_DRIVER       = "org.postgresql.Driver";
    public static final String DB_CONNECTION   = "jdbc:postgresql://" + JDBC_HOST + ":" + JDBC_PORT + "/" + JDBC_DB;
    public static final String DB_USER         = "test";
    public static final String DB_PASSWORD     = "test";


    public static Connection getDBConnection() {
        Connection dbConnection = null;

        try {
            Class.forName(Settings.DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(Settings.DB_CONNECTION, Settings.DB_USER, Settings.DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
}
