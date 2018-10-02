package com.ef.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import lombok.SneakyThrows;

public class DataBaseConnection {

    public static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String JDBC_MYSQL_LOCALHOST_PARSER = "jdbc:mysql://localhost/parser";
    public static final String ROOT = "root";
    public static final String PASSWORD = "123456";
    public static final ComboPooledDataSource cpds = createPooledDataSource();

    @SneakyThrows
    public static Connection createConnection() {
        if(cpds != null) {
            Connection connection = cpds.getConnection();
            connection.setAutoCommit(true);
            return connection;
        }
        return null;
    }

    public static void closeConnection(){
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
        cpds.setMinPoolSize(2);
        cpds.setAcquireIncrement(1);
        cpds.setMaxPoolSize(5);
        return cpds;
    } catch (PropertyVetoException e) {
        e.printStackTrace();
        return null;
    }
    }
}
