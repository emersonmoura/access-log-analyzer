package com.ef.di;

import com.ef.db.BlockedIpRepository;
import com.ef.db.DataBaseConnection;
import com.ef.db.IpRequestRepository;
import com.ef.execute.BlockedIpService;
import com.ef.execute.ParserExecutor;
import com.ef.execute.ParserFile;
import com.ef.input.AccessLogPath;
import com.ef.input.Duration;
import com.ef.input.ConsoleInput;
import com.ef.input.InputValidator;
import com.ef.input.ParserInput;
import com.ef.input.StartDate;
import com.ef.input.Threshold;
import java.util.Arrays;
import java.util.List;

public class DependencyFactory {

    public static ParserExecutor createParserExecutor(){
        return new ParserExecutor(createInputValidator(), createParserFile(), createConsoleInput(), createIpRequestRepository());
    }

    public static InputValidator createInputValidator(){
        List<ParserInput> inputs = createParserInputs();
        return new InputValidator(inputs);
    }

    public static List<ParserInput> createParserInputs() {
        return Arrays.asList(new Duration(), new StartDate(), new Threshold(), new AccessLogPath());
    }

    public static ParserFile createParserFile(){
        return new ParserFile();
    }

    public static ConsoleInput createConsoleInput(){
        return new ConsoleInput(createParserInputs());
    }

    public static IpRequestRepository createIpRequestRepository(){
        return new IpRequestRepository(DataBaseConnection.getConnection());
    }

    public static BlockedIpRepository createdBlockedIpRepository(){
        return new BlockedIpRepository(DataBaseConnection.getConnection());
    }

    public static BlockedIpService createBlockedIpService(){
        return new BlockedIpService(createdBlockedIpRepository(), createConsoleInput());
    }
}
