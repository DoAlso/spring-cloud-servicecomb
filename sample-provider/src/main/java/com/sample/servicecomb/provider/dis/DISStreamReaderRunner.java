package com.sample.servicecomb.provider.dis;

import com.sample.servicecomb.provider.configuration.DISConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DISStreamReaderRunner
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/5 13:34
 */
@Component
public class DISStreamReaderRunner implements ApplicationRunner {

    @Autowired
    private DISStreamDispatcher disStreamDisPatcher;

    @Autowired
    private DISConfiguration disConfiguration;

    private Map<String,Byte> stringMap = new HashMap<>();

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        stringMap.put("dis-face",(byte)1);
        DISStreamPollReader disStreamReader = new DISStreamPollReader();
        disStreamReader.setDisStreamDisPatcher(disStreamDisPatcher);
        DISStreamReaderStarter readerStarter = new DISStreamReaderStarter(stringMap, disStreamReader,disConfiguration);
        readerStarter.start();
    }
}
