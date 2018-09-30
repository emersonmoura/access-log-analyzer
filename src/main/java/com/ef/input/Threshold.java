package com.ef.input;

import java.util.stream.Stream;

public class Threshold implements ParserInput{

    public static final String THRESHOLD = "--threshold=";
    public static final String EMPTY = "";
    public static final String POSITIVE_NUMBER = "^[1-9][0-9]{0,3}$";

    @Override
    public void validate(String[] args) {
        if(args == null){
            throw new IllegalArgumentException("Invalid parameter threshold");
        }

        if(Stream.of(args).anyMatch(this::invalidValues)){
            throw new IllegalArgumentException("Invalid parameter threshold. The value should be a number and greater than zero");
        }
    }

    public String extractValue(String arg){
        return arg.replaceAll(THRESHOLD, EMPTY);
    }

    private boolean invalidValues(String arg){
        return containsDuration(arg) && !containsValidValues(arg);
    }

    private boolean containsDuration(String arg){
        return arg != null && arg.contains(THRESHOLD);
    }

    private boolean containsValidValues(String arg){
        return arg != null && extractValue(arg).matches(POSITIVE_NUMBER);
    }
}
