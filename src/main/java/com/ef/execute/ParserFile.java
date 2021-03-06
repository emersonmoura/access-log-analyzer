package com.ef.execute;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ParserFile {

    public Stream<String> lines(String path){
        try {
            return Files.lines(Paths.get(path));
        } catch (Exception e) {
            System.out.printf("File not found %s%n", e.getMessage());
            return Stream.empty();
        }
    }
}
