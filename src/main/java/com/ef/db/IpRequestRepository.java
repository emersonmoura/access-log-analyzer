package com.ef.db;

import com.ef.model.IpRequest;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.Statement;
import lombok.SneakyThrows;

public class IpRequestRepository {

    private Connection connection;

    public IpRequestRepository(Connection connection) {
        this.connection = connection;
    }

    @SneakyThrows
    public void save(IpRequest ipRequest) {
        try {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(String.format("INSERT INTO IP_REQUEST (DATE, IP, STATUS, REQUEST, USER_AGENT) VALUES ('%s', '%s', %d, '%s', '%s')",
                        ipRequest.getDateAsString(), ipRequest.getIp(), ipRequest.getStatus(), ipRequest.getRequest(), ipRequest.getUserAgent()));
                System.out.println("IP request registered!");
            }
        } catch (MySQLIntegrityConstraintViolationException e) {
        }
    }
}
