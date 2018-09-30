package com.ef;

import com.ef.input.ParserInput;
import java.util.Collection;
import java.util.stream.Stream;

public class InputValidator {

    public static final String VALID_INPUT_FORMAT = "--([a-zA-Z])\\w+=(\\w.*)";
    private Collection<ParserInput> inputs;

    InputValidator(Collection<ParserInput> inputs){
        this.inputs = inputs;
    }

    public void validate(String[] args) {
        checkValidInput(args);
        inputs.forEach(input -> input.validate(args));
    }

    private void checkValidInput(String[] args){
        if(args == null || args.length <= 0){
            throwDefaultException();
            return;
        }
        Stream.of(args).forEach(this::checkArg);
    }

    private void checkArg(String arg){
        if(arg == null || arg.isEmpty()){
            throwDefaultException();
        }
        if(!arg.matches(VALID_INPUT_FORMAT)){
            throw new IllegalArgumentException("Invalid parameter");
        }
    }

    private void throwDefaultException() {
        throw new IllegalArgumentException("You need to inform someone parameter");
    }
}
