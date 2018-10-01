package com.ef.input;

import java.io.File;
import java.util.stream.Stream;

public class AccessLogPath implements ParserInput{

    public static final String ACCESS_LOG = "--accesslog=";
    public static final String EMPTY = "";

    @Override
    public void validate(String[] args) {
        if(args == null || Stream.of(args).anyMatch(this::invalidValues)) {
            throw new IllegalArgumentException("Invalid parameter accessLogPath");
        }
    }

        public String extractValue(String arg){
            return arg.replaceAll(ACCESS_LOG, EMPTY);
        }

        private boolean invalidValues(String arg){
            return containsDuration(arg) && !containsValidValues(arg);
        }

        private boolean containsDuration(String arg){
            return arg != null && arg.contains(ACCESS_LOG);
        }

        private boolean containsValidValues(String arg){
            return arg != null && !extractValue(arg).isEmpty();
        }

    }
