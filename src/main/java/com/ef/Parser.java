package com.ef;

import com.ef.db.DataBaseConnection;
import com.ef.execute.BlockedIpService;

import static com.ef.di.DependencyFactory.createBlockedIpService;
import static com.ef.di.DependencyFactory.createParserExecutor;

public class Parser {

    public static void main(String[] args) {
        try {
            BlockedIpService blockedIpService = createBlockedIpService();
            createParserExecutor().execute(args).forEach(ip -> blockedIpService.register(ip, args));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }finally {
            DataBaseConnection.closeConnection();
            DataBaseConnection.closePool();
        }
    }

}
