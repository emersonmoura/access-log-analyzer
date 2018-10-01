package com.ef.input;

import java.util.stream.Stream;

import static com.ef.input.InputType.DURATION;

public class Duration implements ParserInput {

    public static final String HOURLY = "hourly";
    public static final String DAILY = "daily";
    public static final String EMPTY = "";

    @Override
    public void validate(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("Invalid parameter duration");
        }
        if (Stream.of(args).anyMatch(this::invalidValues)) {
            throw new IllegalArgumentException("Invalid parameter duration. Should be hourly or daily");
        }
    }

    public String extractValue(String arg) {
        return arg.replaceAll(DURATION.getInputName(), EMPTY);
    }

    @Override
    public InputType getType() {
        return DURATION;
    }

    private boolean invalidValues(String arg) {
        return containsDuration(arg) && !containsValidValues(arg);
    }

    private boolean containsDuration(String arg) {
        return arg != null && arg.contains(DURATION.getInputName());
    }

    private boolean containsValidValues(String arg) {
        return arg != null && (arg.contains(HOURLY) || arg.contains(DAILY));
    }

}
