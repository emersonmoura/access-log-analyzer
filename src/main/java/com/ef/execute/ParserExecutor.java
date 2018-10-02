package com.ef.execute;

import com.ef.extract.ColumnExtractor;
import com.ef.extract.LogColumn;
import com.ef.input.ConsoleInput;
import com.ef.input.InputType;
import com.ef.input.InputValidator;
import com.ef.input.StartDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ef.input.InputType.START_DATE;

public class ParserExecutor {

    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    private InputValidator inputValidator;
    private ParserFile file;
    private ConsoleInput consoleInput;

    public ParserExecutor(InputValidator inputValidator,
                          ParserFile file,
                          ConsoleInput consoleInput){
        this.inputValidator = inputValidator;
        this.file = file;
        this.consoleInput = consoleInput;
    }

    public Collection<String> execute(String[] args) {
        inputValidator.validate(args);
        Map<InputType, String> consoleInputs = consoleInput.extract(args);
        String path = consoleInputs.get(InputType.ACCESS_LOG);
        String startDate = consoleInputs.get(START_DATE);
        return file.lines(path)
                .filter(line -> dateInLineIsAfter(line, startDate))
                .map(this::getIP).collect(Collectors.toList());
    }

    private boolean dateInLineIsAfter(String line, String startDateStr){
        String date = new ColumnExtractor().columnOnLine(LogColumn.DATE, line);
        LocalDateTime startDate = getDate(startDateStr, StartDate.DATE_FORMAT);
        LocalDateTime lineDate = getDate(date, YYYY_MM_DD_HH_MM_SS_SSS);
        return lineDate.isAfter(startDate);
    }

    private String getIP(String line){
        String ip = new ColumnExtractor().columnOnLine(LogColumn.IP, line);
        System.out.println(ip);
        return ip;
    }

    private LocalDateTime getDate(String date, String pattern) {
        DateTimeFormatter lineDateFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(date, lineDateFormatter);
    }
}
