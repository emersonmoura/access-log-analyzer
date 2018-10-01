package com.ef;

import com.ef.extract.ColumnExtractor;
import com.ef.extract.LogColumn;
import com.ef.input.AccessLogPath;
import com.ef.input.Duration;
import com.ef.input.InputValidator;
import com.ef.input.ParserInput;
import com.ef.input.StartDate;
import com.ef.input.Threshold;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Parser {

    public static void main(String[] args) throws IOException {
         String path = "";
        List<ParserInput> inputs = Arrays.asList(new Duration(), new StartDate(), new Threshold(), new AccessLogPath());
        new InputValidator(inputs).validate(args);
        Files.lines(Paths.get(path)).forEach(Parser::extract);
    }

    public static void extract(String line){
            new ColumnExtractor().columnOnLine(LogColumn.DATE, line);
    }
}
