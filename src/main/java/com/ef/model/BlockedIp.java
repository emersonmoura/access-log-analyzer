package com.ef.model;

import lombok.Data;

@Data
public class BlockedIp {

    public BlockedIp(String ip, String comment) {
        this.ip = ip;
        this.comment = comment;
    }

    private String id;
    private String ip;
    private String comment;
}
