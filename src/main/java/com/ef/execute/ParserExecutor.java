package com.ef.execute;

import com.ef.extract.ColumnExtractor;
import com.ef.extract.LogColumn;
import com.ef.input.ConsoleInput;
import com.ef.input.Duration;
import com.ef.input.InputType;
import com.ef.input.InputValidator;
import com.ef.input.StartDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.ef.input.InputType.DURATION;
import static com.ef.input.InputType.START_DATE;
import static com.ef.input.InputType.THRESHOLD;

public class ParserExecutor {

    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final int ONE = 1;
    public static final long ZERO = 0L;
    private InputValidator inputValidator;
    private ParserFile file;
    private ConsoleInput consoleInput;

    private ConcurrentHashMap<String, Long> cache = new ConcurrentHashMap<>();

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
        String threshold = consoleInputs.get(THRESHOLD);
        return file.lines(path)
                .filter(line -> dateInLineIsAfter(line, consoleInputs))
                .filter(line -> countIp(line, threshold))
                .map(this::getIP).collect(Collectors.toList());
    }

    private boolean countIp(String line, String threshold) {
        String ip = getIP(line);
        cache.put(ip,cache.getOrDefault(ip, ZERO) + ONE);
        return cache.get(ip) >= Integer.valueOf(threshold);
    }

    private boolean dateInLineIsAfter(String line, Map<InputType, String> consoleInputs){
        String date = new ColumnExtractor().columnOnLine(LogColumn.DATE, line);
        String duration = consoleInputs.get(DURATION);
        LocalDateTime startDate = getDate(consoleInputs.get(START_DATE), StartDate.DATE_FORMAT);
        LocalDateTime tempDate = LocalDateTime.now();
        if(Duration.HOURLY.equals(duration)){
            tempDate = startDate.plusHours(1);
        }
        if(Duration.DAILY.equals(duration)){
            tempDate = startDate.plusDays(1);
        }
        LocalDateTime lineDate = getDate(date, YYYY_MM_DD_HH_MM_SS_SSS);
        return lineDate.isBefore(tempDate) && lineDate.isAfter(startDate);
    }

    private String getIP(String line){
        return new ColumnExtractor().columnOnLine(LogColumn.IP, line);
    }

    private LocalDateTime getDate(String date, String pattern) {
        DateTimeFormatter lineDateFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(date, lineDateFormatter);
    }
}

