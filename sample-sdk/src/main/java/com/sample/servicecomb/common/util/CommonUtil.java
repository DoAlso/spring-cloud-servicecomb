package com.sample.servicecomb.common.util;

import org.apache.commons.lang3.StringUtils;
import sun.net.util.IPAddressUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author huyaxi
 */
public class CommonUtil {
    private static final String POINT_SIGN = ".";
    /**
     * 生成当前任务标识task_uuid'
     */
    public static final char[] HEX_STRING = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    /**
     * 获取32位十六进制随机数
     *
     * @return
     */
    public static String randHexStr() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length=32;
        for (int i = 0; i < length; i++) {
            sb.append(HEX_STRING[random.nextInt(HEX_STRING.length)]);
            if (i == 7) {
                sb.append("-");
            } else if (i == 11) {
                sb.append("-");
            } else if (i == 15) {
                sb.append("-");
            } else if (i == 19) {
                sb.append("-");
            }
        }
        return sb.toString();
    }

    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }


    public static final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        inStream.close();
        swapStream.close();
        return in2b;
    }


    public static boolean internalIp(String ip) {
        byte[] addr = IPAddressUtil.textToNumericFormatV4(ip);
        return internalIp(addr);
    }


    public static boolean ping(String ipAddress){
        int  timeOut =  3000 ;
        boolean status = false;
        try {
            status = InetAddress.getByName(ipAddress).isReachable(timeOut);
        }catch (IOException e){
            e.printStackTrace();
        }
        return status;
    }



    public static boolean internalIp(byte[] addr) {
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        //10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        //172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        //192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0) {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                    return true;
                }
            case SECTION_5:
                switch (b1) {
                    case SECTION_6:
                        return true;
                    default:
                        return false;
                }
            default:
                return false;

        }
    }


    public static String ipReplace(String url,String replaceIp){
        if(StringUtils.isEmpty(url)){
            return null;
        }
        String reg = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
        return url.replaceAll(reg,replaceIp);
    }



    public static String generateRandomFilename(String fileExt){
        Random rand = new Random();
        int random = rand.nextInt();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String temp = sf.format(new Date());
        String randomFilename = new StringBuffer(temp)
                .append(String.valueOf(random > 0 ? random : ( -1) * random))
                .append(POINT_SIGN)
                .append(fileExt).toString();
        return randomFilename;
    }

    public static String generateRandomFilePath(){
        Random rand = new Random();
        int random = rand.nextInt();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String temp = sf.format(new Date());
        String randomFilename = new StringBuffer(temp)
                .append(String.valueOf(random > 0 ? random : ( -1) * random))
                .append(POINT_SIGN)
                .append("\\").toString();
        return randomFilename;
    }
}
