package com.ef.db;

import com.ef.model.BlockedIp;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.Statement;
import lombok.SneakyThrows;

public class BlockedIpRepository {

    private Connection connection;

    public BlockedIpRepository(Connection connection){
        this.connection = connection;
    }

    @SneakyThrows
    public void save(BlockedIp blockedIp){
        try {
            try (Statement statement = connection.createStatement()) {
                statement.addBatch(String.format("INSERT INTO BLOCKED_IP (IP, COMMENT) VALUES ('%s', '%s')", blockedIp.getIp(), blockedIp.getComment()));
            }
        }catch (MySQLIntegrityConstraintViolationException e){
        }
    }
}
