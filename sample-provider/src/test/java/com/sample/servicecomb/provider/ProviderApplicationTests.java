package com.sample.servicecomb.provider;

import com.huaweicloud.frs.client.result.DetectFaceResult;
import com.sample.servicecomb.common.frs.FrsClientUtil;
import com.sample.servicecomb.common.util.FastJsonUtil;
import com.sample.servicecomb.provider.service.FileService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProviderApplicationTests {
    private static Logger logger = LoggerFactory.getLogger(ProviderApplicationTests.class);
    @Autowired
    private FrsClientUtil frsClientUtil;
    @Autowired
    private FileService fileService;

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
    public void testObsDownLoadV1() throws Exception{
        Map<String,String> map = new HashMap<>();
        map.put("20190301103037791085876.jpg","faces/13267/20190301103037791085876.jpg");
        map.put("20190301103037855209167.jpg","faces/13267/20190301103037855209167.jpg");
        map.put("201903011030481388042893.jpg","faces/13267/201903011030481388042893.jpg");
        map.put("201903011030501727023809.jpg","faces/13267/201903011030501727023809.jpg");
        fileService.download("hoolink-bucket",map);
    }
}
