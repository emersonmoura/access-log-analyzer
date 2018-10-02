package com.ef.execute;

import com.ef.db.BlockedIpRepository;
import com.ef.input.ConsoleInput;
import com.ef.input.InputType;
import com.ef.model.BlockedIp;
import java.util.Map;

import static com.ef.input.InputType.*;

public class BlockedIpService {

    private BlockedIpRepository blockedIpRepository;
    private ConsoleInput consoleInput;

    public BlockedIpService(BlockedIpRepository blockedIpRepository, ConsoleInput consoleInput){
        this.blockedIpRepository = blockedIpRepository;
        this.consoleInput = consoleInput;
    }

    public void register(String ip, String[] args){
        blockedIpRepository.save(new BlockedIp(ip, createComment(args)));
        System.out.println(ip);
    }

    private String createComment(String[] args){
        Map<InputType, String> inputs = consoleInput.extract(args);
        return String.format("threshold=%s duration=%s startDate=%s",
                inputs.get(THRESHOLD), inputs.get(DURATION), inputs.get(START_DATE));
    }
}
