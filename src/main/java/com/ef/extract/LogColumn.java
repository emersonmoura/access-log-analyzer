package com.ef.extract;

public enum LogColumn {

    DATE(0), IP(1), REQUEST(2), STATUS(3), USER_AGENT(4);

    private Integer columnIndex;

    LogColumn(Integer columnIndex){
        this.columnIndex = columnIndex;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public static int totalOfColumns() {
        return values().length;
    }
}
