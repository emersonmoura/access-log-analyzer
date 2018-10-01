package com.ef;

import com.ef.di.DependencyFactory;
import com.ef.execute.ParserExecutor;
import java.io.IOException;

public class Parser {

    public static void main(String[] args) throws IOException {
        new ParserExecutor(DependencyFactory.createInputValidator()).execute(args);
    }

}
