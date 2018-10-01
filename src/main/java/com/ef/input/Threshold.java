package com.ef.input;

import java.util.stream.Stream;

import static com.ef.input.InputType.THRESHOLD;

public class Threshold implements ParserInput {

    public static final String EMPTY = "";
    public static final String POSITIVE_NUMBER = "^[1-9][0-9]{0,3}$";

    @Override
    public void validate(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("Invalid parameter threshold");
        }

        if (Stream.of(args).anyMatch(this::invalidValues)) {
            throw new IllegalArgumentException("Invalid parameter threshold. The value should be a number and greater than zero");
        }
    }

    public String extractValue(String arg) {
        return arg.replaceAll(THRESHOLD.getInputName(), EMPTY);
    }

    private boolean invalidValues(String arg) {
        return containsThreshold(arg) && !containsValidValues(arg);
    }

    @Override
    public InputType getType() {
        return THRESHOLD;
    }

    private boolean containsThreshold(String arg) {
        return arg != null && arg.contains(THRESHOLD.getInputName());
    }

    private boolean containsValidValues(String arg) {
        return arg != null && extractValue(arg).matches(POSITIVE_NUMBER);
    }
}
