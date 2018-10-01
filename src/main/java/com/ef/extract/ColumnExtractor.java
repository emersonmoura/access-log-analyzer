package com.ef.extract;

public class ColumnExtractor {

    public static final String SPLIT_PATTERN = "\\|";

    public String columnOnLine(LogColumn header, String line){
        return line.split(SPLIT_PATTERN)[header.getColumnIndex()];
    }

}
