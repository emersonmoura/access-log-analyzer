package com.ef;

import com.ef.input.Duration;
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
        List<ParserInput> inputs = Arrays.asList(new Duration(), new StartDate(), new Threshold());
        new InputValidator(inputs).validate(args);
        Files.lines(Paths.get(path)).forEach(Parser::extract);
    }

    public static void extract(String line){
            new Extractor().extract(line);
    }
}
