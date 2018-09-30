package com.ef.input;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;

public class StartDate implements ParserInput{

    public static final String START_DATE = "--startDate=";

    @Override
    public void validate(String[] args) {
        if(args == null){
            throw new IllegalArgumentException("Invalid parameter startDate");
        }
        if(Stream.of(args).anyMatch(this::invalidValues)){
            throw new IllegalArgumentException("Invalid parameter startDate. The format Should be yyyy-MM-dd.HH:mm:ss");
        }
    }

    public String extractValue(String arg){
        return arg.replaceAll(START_DATE, "");
    }

    private boolean invalidValues(String arg){
        return containsStartDate(arg) && !containsValidValues(arg);
    }

    private boolean containsStartDate(String arg){
        return arg != null && arg.contains(START_DATE);
    }

    private boolean containsValidValues(String arg){
        return arg != null && validateDateFormat(extractValue(arg));
    }

    private boolean validateDateFormat(String input) {
        if(input == null) {
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
        try {
            format.parse(input);
            return true;
        } catch(ParseException e) {
            return false;
        }
    }
}
