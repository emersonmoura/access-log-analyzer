package com.ef;

import static com.ef.di.DependencyFactory.createParserExecutor;

public class Parser {

    public static void main(String[] args) {
        try {
            createParserExecutor().execute(args).forEach(System.out::println);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

}
