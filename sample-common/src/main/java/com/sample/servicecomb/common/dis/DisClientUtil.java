package com.sample.servicecomb.common.dis;

import com.huaweicloud.dis.DIS;
import com.huaweicloud.dis.DISClientBuilder;
import com.huaweicloud.dis.iface.data.request.GetPartitionCursorRequest;
import com.huaweicloud.dis.iface.data.request.GetRecordsRequest;
import com.huaweicloud.dis.iface.data.response.GetPartitionCursorResult;
import com.huaweicloud.dis.iface.data.response.GetRecordsResult;
import com.huaweicloud.dis.iface.data.response.Record;
import com.huaweicloud.dis.util.PartitionCursorTypeEnum;
import com.sample.servicecomb.common.configuration.DisConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

/**
 * @ClassName DisClientUtil
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/7 15:22
 */
public class DisClientUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisClientUtil.class);
    private DisConfigurationProperties disProperties;
    private DIS dis;
    private String stream;
    private DisRecordHandler disRecordHandler;

    public DIS getInstance() {
        if (dis == null) {
            synchronized (DisClientUtil.class) {
                if (dis == null) {
                    dis = createDisClient();
                }
            }
        }
        return dis;
    }

    public void reader() {
        new Thread(() -> {
            LOGGER.info("start read stream from {}", stream);
            GetPartitionCursorRequest request = new GetPartitionCursorRequest();
            request.setStreamName(stream);
            request.setPartitionId(disProperties.getPartitionId());
            request.setTimestamp(System.currentTimeMillis());
            request.setCursorType(PartitionCursorTypeEnum.AT_TIMESTAMP.name());
            GetPartitionCursorResult response = dis.getPartitionCursor(request);
            String cursor = response.getPartitionCursor();
            LOGGER.info("Get stream {}[partitionId={}] cursor success : {}", stream, disProperties.getPartitionId(), cursor);
            GetRecordsRequest recordsRequest = new GetRecordsRequest();
            while (true) {
                recordsRequest.setPartitionCursor(cursor);
                GetRecordsResult recordResponse = dis.getRecords(recordsRequest);
                // 下一批数据游标
                cursor = recordResponse.getNextPartitionCursor();
                List<Record> records = recordResponse.getRecords();
                Iterator<Record> iterator = records.iterator();
                while (iterator.hasNext()) {
                    Record record = iterator.next();
                    try {
                        disRecordHandler.handler(record);
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage());
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
            }
        },"Thread-"+stream).start();

    }


    private DIS createDisClient() {
        return DISClientBuilder.standard()
                .withRegion(disProperties.getRegion())
                .withEndpoint(disProperties.getEndPoint())
                .withAk(disProperties.getAccessKey())
                .withSk(disProperties.getSecretKey())
                .withProjectId(disProperties.getProjectId())
                .build();
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public void setDisProperties(DisConfigurationProperties properties) {
        this.disProperties = properties;
    }

    public void setDisRecordHandler(DisRecordHandler disRecordHandler) {
        this.disRecordHandler = disRecordHandler;
    }
}
