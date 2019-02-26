package com.sample.servicecomb.provider.utils;

import com.huaweicloud.dis.DIS;
import com.huaweicloud.dis.DISClientBuilder;
import com.huaweicloud.dis.iface.data.request.StreamType;
import com.huaweicloud.dis.iface.stream.request.CreateStreamRequest;
import com.huaweicloud.dis.iface.stream.request.DeleteStreamRequest;
import com.huaweicloud.dis.util.DataTypeEnum;
import com.sample.servicecomb.provider.configuration.DISConfiguration;
import com.sample.servicecomb.provider.dis.DISStreamReader;

/**
 * @author Hyx
 */
public class DISUtil {

    private static DIS dis;

    private static DISStreamReader diStreamReader;

    private static String streamName;

    private static Byte disStreamType;

    private static DISConfiguration configuration;

    public static DIS getInstance() {
        if (dis == null) {
            synchronized (DISUtil.class) {
                if (dis == null) {
                    dis = createDISClient();
                }
            }
        }
        return dis;
    }

    private static DIS createDISClient() {
        return DISClientBuilder.standard()
                .withRegion(configuration.getRegion())
                .withEndpoint(configuration.getEndPoint())
                .withAk(configuration.getAccessKey())
                .withSk(configuration.getSecretKey())
                .withProjectId(configuration.getProjectId())
                .build();
    }

    public static void reader(){
        diStreamReader.reader(dis,streamName,disStreamType);
    }


    /**
     * 创建DIS流
     * @throws Exception
     */
    public static void createStream() throws Exception{
        CreateStreamRequest createStreamRequest = new CreateStreamRequest();
        createStreamRequest.setStreamName(streamName);
        // COMMON 普通通道; ADVANCED 高级通道
        createStreamRequest.setStreamType(StreamType.COMMON.name());
        createStreamRequest.setDataType(DataTypeEnum.BLOB.name());
        createStreamRequest.setPartitionCount(1);
        createStreamRequest.setDataDuration(24);
        dis.createStream(createStreamRequest);
    }

    /**
     * 删除DIS流
     * @throws Exception
     */
    public static void deleteStream() throws Exception{
        DeleteStreamRequest deleteStreamRequest = new DeleteStreamRequest();
        deleteStreamRequest.setStreamName(streamName);
        dis.deleteStream(deleteStreamRequest);
    }

    public static void setDiStreamReader(DISStreamReader diStreamReader) {
        DISUtil.diStreamReader = diStreamReader;
    }

    public static void setStreamName(String streamName) {
        DISUtil.streamName = streamName;
    }

    public static void setDisStreamType(Byte disStreamType) {
        DISUtil.disStreamType = disStreamType;
    }

    public static void setConfiguration(DISConfiguration configuration) {
        DISUtil.configuration = configuration;
    }
}
