package com.ef.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;

@Data
public class IpRequest {


    public static final String DATE_FORMAT = "YYYY-MM-DD HH:MI:SS";

    public IpRequest(LocalDateTime date, String ip, String status, String request, String userAgent) {
        this.date = date;
        this.ip = ip;
        this.status = Integer.valueOf(status);
        this.request = request;
        this.userAgent = userAgent;
    }

    private String id;
    private LocalDateTime date;
    private String ip;
    private Integer status;
    private String request;
    private String userAgent;

    public String getDateAsString() {
        DateTimeFormatter lineDateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return lineDateFormatter.format(this.date);
    }
}
