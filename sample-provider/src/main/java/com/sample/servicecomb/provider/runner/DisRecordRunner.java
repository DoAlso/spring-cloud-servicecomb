package com.sample.servicecomb.provider.runner;

import com.sample.servicecomb.common.dis.DisClientUtil;
import com.sample.servicecomb.common.dis.DisRecordHandler;
import com.sample.servicecomb.provider.service.FaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName DisRecordRunner
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/7 16:51
 */
@Component
public class DisRecordRunner implements ApplicationRunner {
    @Autowired
    private DisClientUtil disClientUtil;
    @Autowired
    private DisRecordHandler disRecordHandler;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        disClientUtil.setStream("dis-face");
        disClientUtil.setDisRecordHandler(disRecordHandler);
        disClientUtil.reader();
    }
}
