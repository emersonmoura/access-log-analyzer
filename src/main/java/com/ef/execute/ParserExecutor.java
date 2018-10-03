package com.ef.execute;

import com.ef.db.IpRequestRepository;
import com.ef.extract.ColumnExtractor;
import com.ef.extract.LogColumn;
import com.ef.input.ConsoleInput;
import com.ef.input.Duration;
import com.ef.input.InputType;
import com.ef.input.InputValidator;
import com.ef.input.StartDate;
import com.ef.model.IpRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.ef.extract.LogColumn.*;
import static com.ef.input.InputType.*;
import static com.ef.input.InputType.DURATION;
import static com.ef.input.InputType.START_DATE;
import static com.ef.input.InputType.THRESHOLD;

public class ParserExecutor {

    public static final String LINE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final int ONE = 1;
    public static final long ZERO = 0L;
    private InputValidator inputValidator;
    private ParserFile file;
    private ConsoleInput consoleInput;
    private IpRequestRepository requestRepository;

    private ConcurrentHashMap<String, Long> ipCount = new ConcurrentHashMap<>();

    public ParserExecutor(InputValidator inputValidator,
                          ParserFile file,
                          ConsoleInput consoleInput,
                          IpRequestRepository requestRepository){
        this.inputValidator = inputValidator;
        this.file = file;
        this.consoleInput = consoleInput;
        this.requestRepository = requestRepository;
    }

    public Collection<String> execute(String[] args) {
        inputValidator.validate(args);
        System.out.println("Processing...");
        Collection<String> ips = execute(consoleInput.extract(args));
        requestRepository.flush();
        requestRepository.close();
        return ips;
    }

    private Collection<String> execute(Map<InputType, String> consoleInputs) {
        String path = consoleInputs.get(ACCESS_LOG);
        String threshold = consoleInputs.get(THRESHOLD);
        int[] count = new int[]{1};
        return file.lines(path)
                .filter(line -> {
                    sendToDataBase(line, count);
                    return lineDateAccordingInputParams(line, consoleInputs);
                })
                .filter(line -> matchCountIpEvents(line, threshold))
                .map(this::getIp).collect(Collectors.toSet());
    }

    private void sendToDataBase(String line, int[] count){
        IpRequest ipRequest = new IpRequest(createDate(
                getField(line, DATE), LINE_DATE_FORMAT),
                getField(line, IP),
                getField(line, STATUS),
                getField(line, REQUEST),
                getField(line, USER_AGENT));
        count[0] = count[0] + ONE;
        requestRepository.addBatch(ipRequest, count[0]);
    }

    private boolean matchCountIpEvents(String line, String threshold) {
        String ip = getIp(line);
        ipCount.put(ip, ipCount.getOrDefault(ip, ZERO) + ONE);
        return ipCount.get(ip) >= Integer.valueOf(threshold);
    }

    private boolean lineDateAccordingInputParams(String line, Map<InputType, String> consoleInputs){
        LocalDateTime startDate = createDate(consoleInputs.get(START_DATE), StartDate.DATE_FORMAT);
        LocalDateTime durationDate = createDurationTime(consoleInputs.get(DURATION), startDate);
        LocalDateTime lineDate = createDate(getField(line, DATE), LINE_DATE_FORMAT);
        return lineDate.isBefore(durationDate) && lineDate.isAfter(startDate);
    }

    private LocalDateTime createDurationTime(String duration, LocalDateTime startDate) {
        if(Duration.HOURLY.equals(duration)){
            return startDate.plusHours(ONE);
        }
        return startDate.plusDays(ONE);
    }

    private LocalDateTime createDate(String date, String pattern) {
        DateTimeFormatter lineDateFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(date, lineDateFormatter);
    }

    private String getField(String line, LogColumn date) {
        return new ColumnExtractor().columnOnLine(date, line);
    }

    private String getIp(String line){
        return getField(line, IP);
    }
}

