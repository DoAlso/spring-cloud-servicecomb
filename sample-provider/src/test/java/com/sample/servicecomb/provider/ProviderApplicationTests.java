package com.sample.servicecomb.provider;

import com.huaweicloud.frs.client.result.DetectFaceResult;
import com.obs.services.model.DownloadFileResult;
import com.sample.servicecomb.common.frs.FrsClientUtil;
import com.sample.servicecomb.common.obs.ObsClientUtil;
import com.sample.servicecomb.common.util.FastJsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProviderApplicationTests {
    private static Logger logger = LoggerFactory.getLogger(ProviderApplicationTests.class);
    @Autowired
    private FrsClientUtil frsClientUtil;
    @Autowired
    private ObsClientUtil obsClientUtil;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testDetectFace(){
        try {
            DetectFaceResult detectFaceResult = frsClientUtil.detectFace("/hoolink-bucket/84c63e80-e3a5-43ab-8d45-5fc07a27e81c/1552353798/20190312092318325404103.jpg", "1,2,4,5");
            logger.info("detectFaceResult:{}", FastJsonUtil.toJSONString(detectFaceResult));
            Assert.assertEquals("male",detectFaceResult.getFaces().get(0).getAttributes().getGender());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testObsDownLoad(){
        DownloadFileResult result = obsClientUtil.downloadObject("hoolink-bucket","faces/13267/20190301103157121596194.jpg","20190301103157121596194.jpg");
        logger.info("detectFaceResult:{}", FastJsonUtil.toJSONString(result));
    }
}
