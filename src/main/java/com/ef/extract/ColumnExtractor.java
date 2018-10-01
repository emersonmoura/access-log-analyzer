package com.ef.extract;

public class ColumnExtractor {

    public static final String SPLIT_PATTERN = "\\|";

    public String columnOnLine(LogColumn header, String line){
        String[] columns = line.split(SPLIT_PATTERN);
        validate(columns);
        return columns[header.getColumnIndex()];
    }

    private void validate(String[] columns) {
        if(columns.length != LogColumn.totalOfColumns()){
            throw new IllegalStateException("Line without right size");
        }
    }

}
