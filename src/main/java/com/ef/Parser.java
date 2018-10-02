package com.ef;

import com.ef.execute.ParserExecutor;

import static com.ef.di.DependencyFactory.createInput;
import static com.ef.di.DependencyFactory.createInputValidator;
import static com.ef.di.DependencyFactory.createParserFile;

public class Parser {

    public static void main(String[] args) {
        new ParserExecutor(createInputValidator(), createParserFile(), createInput())
                .execute(args).forEach(System.out::println);
    }

}
