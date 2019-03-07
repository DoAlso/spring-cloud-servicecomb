package com.sample.servicecomb.common.dis;

import com.huaweicloud.dis.DIS;
import com.huaweicloud.dis.DISClientAsync2;
import com.huaweicloud.dis.DISClientBuilder;
import com.huaweicloud.dis.DISConfig;
import com.huaweicloud.dis.iface.data.request.GetPartitionCursorRequest;
import com.huaweicloud.dis.iface.data.request.GetRecordsRequest;
import com.huaweicloud.dis.iface.data.response.GetPartitionCursorResult;
import com.huaweicloud.dis.iface.data.response.GetRecordsResult;
import com.huaweicloud.dis.iface.data.response.Record;
import com.huaweicloud.dis.util.PartitionCursorTypeEnum;
import com.sample.servicecomb.common.configuration.DisConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * @ClassName DisClientBuilder
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/7 15:22
 */
@Component
public class DisClientBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisClientBuilder.class);
    @Autowired
    private DisConfigurationProperties properties;
    private DIS dis;
    private String stream;
    private DisRecordHandler disRecordHandler;

    public  DIS bulid() {
        if (dis == null) {
            synchronized (DisClientBuilder.class) {
                if (dis == null) {
                    dis = createDisClient();
                }
            }
        }
        return dis;
    }

    public void reader(){
        LOGGER.info("start read stream from {}",stream);
        GetPartitionCursorRequest request = new GetPartitionCursorRequest();
        request.setStreamName(stream);
        request.setPartitionId(properties.getPartitionId());
        request.setTimestamp(System.currentTimeMillis());
        request.setCursorType(PartitionCursorTypeEnum.AT_TIMESTAMP.name());
        GetPartitionCursorResult response = dis.getPartitionCursor(request);
        String cursor = response.getPartitionCursor();
        LOGGER.info("Get stream {}[partitionId={}] cursor success : {}", stream,properties.getPartitionId(), cursor);
        GetRecordsRequest recordsRequest = new GetRecordsRequest();
        while(true){
            recordsRequest.setPartitionCursor(cursor);
            GetRecordsResult recordResponse = dis.getRecords(recordsRequest);
            // 下一批数据游标
            cursor = recordResponse.getNextPartitionCursor();
            List<Record> records = recordResponse.getRecords();
            Iterator<Record> iterator = records.iterator();
            while (iterator.hasNext()){
                Record record = iterator.next();
                try {
                    disRecordHandler.handler(record);
                }catch (Exception e){
                    LOGGER.error(e.getMessage());
                }
            }
            try {
                Thread.sleep(1000);
            }catch (Exception e){

            }
        }
    }


    private DIS createDisClient() {
        return DISClientBuilder.standard()
                .withRegion(properties.getRegion())
                .withEndpoint(properties.getEndPoint())
                .withAk(properties.getAccessKey())
                .withSk(properties.getSecretKey())
                .withProjectId(properties.getProjectId())
                .build();
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public void setDisRecordHandler(DisRecordHandler disRecordHandler) {
        this.disRecordHandler = disRecordHandler;
    }
}
