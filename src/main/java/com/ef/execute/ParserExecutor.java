package com.ef.execute;

import com.ef.extract.ColumnExtractor;
import com.ef.extract.LogColumn;
import com.ef.input.InputValidator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParserExecutor {

    private InputValidator inputValidator;

    public ParserExecutor(InputValidator inputValidator){
        this.inputValidator = inputValidator;
    }

    public void execute(String[] args) throws IOException {
        String path = "";
        inputValidator.validate(args);
        Files.lines(Paths.get(path)).forEach(this::extract);
    }

    public void extract(String line){
        new ColumnExtractor().columnOnLine(LogColumn.DATE, line);
    }
}
