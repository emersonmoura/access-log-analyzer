package com.ef.db;

import com.ef.model.IpRequest;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.SneakyThrows;

public class IpRequestRepository {

    public static final int MAX_TO_FLUSH = 1500;
    private Connection connection;
    private Statement statement;

    @SneakyThrows
    public IpRequestRepository(Connection connection) {
        this.connection = connection;
        if(connection != null) {
            statement = connection.createStatement();
        }
    }

    @SneakyThrows
    public void addBatch(IpRequest ipRequest, Integer count) {
        statement.addBatch(String.format("INSERT INTO IP_REQUEST (DATE, IP, STATUS, REQUEST, USER_AGENT) VALUES ('%s', '%s', %d, '%s', '%s')",
                    ipRequest.getDateAsString(), ipRequest.getIp(), ipRequest.getStatus(), ipRequest.getRequest(), ipRequest.getUserAgent()));
        if(count % MAX_TO_FLUSH == 0){
            flush();
        }
    }

    public void flush(){
        try {
            if(statement != null) {
                statement.executeBatch();
            }
        } catch (SQLException e) {
        }
    }

    public void close(){
        try {
            if(statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public void save(IpRequest ipRequest) {
        try {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(String.format("INSERT INTO IP_REQUEST (DATE, IP, STATUS, REQUEST, USER_AGENT) VALUES ('%s', '%s', %d, '%s', '%s')",
                        ipRequest.getDateAsString(), ipRequest.getIp(), ipRequest.getStatus(), ipRequest.getRequest(), ipRequest.getUserAgent()));
            }
        } catch (MySQLIntegrityConstraintViolationException e) {
        }
    }

}
