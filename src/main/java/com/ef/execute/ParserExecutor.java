package com.ef.execute;

import com.ef.extract.ColumnExtractor;
import com.ef.extract.LogColumn;
import com.ef.input.Input;
import com.ef.input.InputType;
import com.ef.input.InputValidator;
import java.util.Collection;
import java.util.Collections;

public class ParserExecutor {

    private InputValidator inputValidator;
    private ParserFile file;
    private Input input;

    public ParserExecutor(InputValidator inputValidator,
                          ParserFile file,
                          Input input){
        this.inputValidator = inputValidator;
        this.file = file;
        this.input = input;
    }

    public Collection<String> execute(String[] args) {
        inputValidator.validate(args);
        String path = input.extract(args).get(InputType.ACCESS_LOG);
        file.lines(path).forEach(this::extract);
        return Collections.emptyList();
    }

    private void extract(String line){
        new ColumnExtractor().columnOnLine(LogColumn.DATE, line);
    }
}
