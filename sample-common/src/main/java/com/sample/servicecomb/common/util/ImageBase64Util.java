package com.sample.servicecomb.common.util;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;

/**
 * @ClassName ImageBase64Util
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/28 9:31
 */
public class ImageBase64Util {

    /**
     * @Function: base64编码图片转 MultipartFile
     * 注意 转码时 需要把data:image/png;base64,这个前缀给去掉
     */
    public MultipartFile base64ToMultipart(String base64) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(base64);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new Base64DecodeMultipartFile(b, base64);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ImageBase64Util builder(){
        return new ImageBase64Util();
    }

    class Base64DecodeMultipartFile implements MultipartFile {
        private final byte[] imgContent;
        private final String header;

        public Base64DecodeMultipartFile(byte[] imgContent, String header) {
            this.imgContent = imgContent;
            this.header = header.split(";")[0];
        }

        @Override
        public String getName() {
            return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
        }

        @Override
        public String getOriginalFilename() {
            return System.currentTimeMillis() + (int)Math.random() * 10000 + "." + header.split("/")[1];
        }

        @Override
        public String getContentType() {
            return header.split(":")[1];
        }

        @Override
        public boolean isEmpty() {
            return imgContent == null || imgContent.length == 0;
        }

        @Override
        public long getSize() {
            return imgContent.length;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return imgContent;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(imgContent);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            new FileOutputStream(dest).write(imgContent);
        }
    }
}
