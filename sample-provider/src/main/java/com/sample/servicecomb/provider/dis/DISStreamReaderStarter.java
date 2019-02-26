package com.sample.servicecomb.provider.dis;

import com.sample.servicecomb.provider.configuration.DISConfiguration;
import com.sample.servicecomb.provider.utils.DISUtil;

import java.util.Map;

/**
 * @ClassName DISStreamReaderStarter
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/1 16:53
 */
public class DISStreamReaderStarter {

    private Map<String,Byte> projectStreams;

    private DISStreamReader diStreamReader;

    private DISConfiguration disConfiguration;

    public DISStreamReaderStarter(Map<String,Byte> projectStreams, DISStreamReader streamReader,DISConfiguration disConfiguration){
        this.projectStreams = projectStreams;
        this.diStreamReader = streamReader;
        this.disConfiguration = disConfiguration;
    }

    /**
     * 开启所有项目的读流操作
     */
    public void start(){
        DISUtil.setConfiguration(disConfiguration);
        DISUtil.getInstance();
        DISUtil.setDiStreamReader(diStreamReader);
        projectStreams.forEach((key,value)-> {
            DISUtil.setStreamName(key);
            DISUtil.setDisStreamType(value);
            DISUtil.reader();
        });
    }
}
