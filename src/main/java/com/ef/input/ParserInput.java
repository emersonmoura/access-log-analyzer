package com.ef.input;

import java.util.stream.Stream;

public interface ParserInput {

    void validate(String[] args);

    default String extract(String[] args) {
        validate(args);
        return Stream.of(args).map(this::extractValue).findFirst().orElse(null);
    }

    String extractValue(String arg);

    default boolean InputNameIs(String name) {
        return name != null && name.contains(getType().getInputName());
    }

    InputType getType();

}
