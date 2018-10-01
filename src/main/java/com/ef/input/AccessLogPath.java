package com.ef.input;

import java.util.stream.Stream;

import static com.ef.input.InputType.*;

public class AccessLogPath implements ParserInput {

    public static final String EMPTY = "";

    @Override
    public void validate(String[] args) {
        if (args == null || Stream.of(args).anyMatch(this::invalidValues)) {
            throw new IllegalArgumentException("Invalid parameter accessLogPath");
        }
    }

    public String extractValue(String arg) {
        return arg.replaceAll(ACCESS_LOG.getInputName(), EMPTY);
    }


    @Override
    public InputType getType() {
        return ACCESS_LOG;
    }

    private boolean invalidValues(String arg) {
        return containsDuration(arg) && !containsValidValues(arg);
    }

    private boolean containsDuration(String arg) {
        return arg != null && arg.contains(ACCESS_LOG.getInputName());
    }

    private boolean containsValidValues(String arg) {
        return arg != null && !extractValue(arg).isEmpty();
    }

}
