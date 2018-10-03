package com.ef.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import lombok.SneakyThrows;

public class DataBaseConnection {

    public static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String JDBC_MYSQL_LOCALHOST_PARSER = "jdbc:mysql://localhost/PARSER";
    public static final String ROOT = "root";
    public static final String PASSWORD = "123456";
    public static ComboPooledDataSource cpds ;
    public static Connection conn;

    @SneakyThrows
    public static Connection getConnection() {
        if(cpds == null){
            cpds = createPooledDataSource();
        }
        if(cpds != null && conn == null) {
            conn = cpds.getConnection();
            conn.setAutoCommit(false);
            return conn;
        }
        return conn;
    }

    public static void closeConnection(){
        if(conn != null){
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closePool(){
        if(cpds != null){
            cpds.close();
        }
    }

    private static ComboPooledDataSource createPooledDataSource() {
     try{
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass(COM_MYSQL_JDBC_DRIVER);
        cpds.setJdbcUrl(JDBC_MYSQL_LOCALHOST_PARSER);
        cpds.setUser(ROOT);
        cpds.setPassword(PASSWORD);
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(2);
        cpds.setMaxPoolSize(15);
        return cpds;
    } catch (PropertyVetoException e) {
        e.printStackTrace();
        return null;
    }
    }
}
