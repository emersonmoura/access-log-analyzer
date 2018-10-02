package com.ef.input;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.util.Pair;

public class ConsoleInput {

    private final Collection<ParserInput> inputs;

    public ConsoleInput(Collection<ParserInput> inputs) {
        this.inputs = inputs;
    }

    public Map<InputType,String> extract(String[] args){
        return Stream.of(args)
                .map(arg -> new Pair<>(getInputParse(arg).getType(), getInputParse(arg).extractValue(arg)))
                .collect(Collectors.toMap(Pair::getKey,Pair::getValue));
    }

    private ParserInput getInputParse(String arg){
        return inputs.stream()
                .filter(input -> input.InputNameIs(arg))
                .findFirst().orElseThrow(()-> new IllegalStateException("Invalid input"));
    }

}


