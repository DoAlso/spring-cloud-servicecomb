package com.sample.servicecomb.common.util;

/**
 * @ClassName FileUtils
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/7 18:53
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author s.watson
 */
public class FileTools {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileTools.class);
    private static byte[] ZIP_HEADER_1 = new byte[] { 80, 75};
    private static byte[] ZIP_HEADER_2 = new byte[] { 82, 97 };
    public FileTools() {

    }


    /**
     * formatPath 转义文件目录
     *
     * @param path
     * @return
     */
    public static String formatPath(String path) {
        return path.replaceAll("\\\\", "/");
    }

    /**
     * combainPath文件路径合并
     *
     * @param eins
     * @param zwei
     * @return
     */
    private static String combainPath(String eins, String zwei) {
        String dori = "";
        eins = null == eins ? "" : formatPath(eins);
        zwei = null == zwei ? "" : formatPath(zwei);
        if (!eins.endsWith("/") && zwei.indexOf("/") != 0) {
            dori = eins + "/" + zwei;
        } else {
            dori = (eins + zwei).replaceAll("//", "/");
        }
        return dori;
    }

    /**
     * list2Array 列表转换数组
     *
     * @param list
     * @return
     */
    private static String[] list2Array(List list) {
        String array[] = (String[]) list.toArray(new String[list.size()]);
        return array;
    }

    /**
     * cp 复制文件
     *
     * @param source
     * @param destination
     * @param loop
     * @return
     */
    public static List<File> cp(String source, String destination, boolean loop) {
        List<File> list = new ArrayList();
        try {
            File srcFile = new File(source);
            File desFile = new File(destination);
            list.addAll(cp(srcFile, desFile, loop));
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return list;
    }

    /**
     * cp 复制文件
     *
     * @param source
     * @param destination
     * @param loop
     * @return
     */
    public static List<File> cp(File source, File destination, boolean loop) {
        List<File> list = new ArrayList();
        try {
            if (!source.exists() || source.isDirectory()) {
                throw new FileNotFoundException();
            }
            list.add(cp(source, destination));
            if (loop) {
                String[] subFile = source.list();
                for (String subPath : subFile) {
                    String src = combainPath(source.getPath(), subPath);//子文件原文件路径
                    String des = combainPath(destination.getPath(), subPath);//子文件目标路径
                    File subfile = new File(src);
                    if (subfile.isFile()) {
                        list.add(cp(src, des));
                    } else if (subfile.isDirectory() && loop) {
                        list.addAll(cp(src, des, loop));
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return list;
    }

    /**
     * cp 单文件复制文件
     *
     * @param source
     * @param destination
     * @return
     */
    public static File cp(String source, String destination) {
        File desFile = null;
        try {
            File srcFile = new File(source);
            desFile = new File(destination);
            desFile = cp(srcFile, desFile);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return desFile;
    }

    /**
     * cp 单文件复制文件
     *
     * @param source
     * @param destination
     * @return
     */
    public static File cp(File source, File destination) {
        FileInputStream in = null;
        FileOutputStream out = null;
        FileChannel inc = null;
        FileChannel outc = null;
        try {
            if (!source.exists() || source.isDirectory()) {
                throw new FileNotFoundException();
            }
            if (source.getPath().equals(destination.getPath())) {
                return source;
            }
            long allbytes = du(source, false);
            if (!destination.exists()) {
                destination.createNewFile();
            }
            in = new FileInputStream(source.getPath());
            out = new FileOutputStream(destination);
            inc = in.getChannel();
            outc = out.getChannel();
            ByteBuffer byteBuffer = null;
            long length = 2097152;//基本大小，默认2M
            long _2M = 2097152;
            while (inc.position() < inc.size()) {
                if (allbytes > (64 * length)) {//如果文件大小大于128M 缓存改为64M
                    length = 32 * _2M;
                } else if (allbytes > (32 * length)) {//如果文件大小大于64M 缓存改为32M
                    length = 16 * _2M;
                } else if (allbytes > (16 * length)) {//如果文件大小大于32M 缓存改为16M
                    length = 8 * _2M;
                } else if (allbytes > (8 * length)) {//如果文件大小大于16M 缓存改为8M
                    length = 4 * _2M;
                } else if (allbytes > (4 * length)) {//如果文件大小大于8M 缓存改为4M
                    length = 2 * _2M;
                } else if (allbytes > (2 * length)) {//如果文件大小大于4M 缓存改为2M
                    length = _2M;
                } else if (allbytes > (length)) {//如果文件大小大于2M 缓存改为1M
                    length = _2M / 2;
                } else if (allbytes < length) {//如果文件小于基本大小，直接输出
                    length = allbytes;
                }
                allbytes = inc.size() - inc.position();
                byteBuffer = ByteBuffer.allocateDirect((int) length);
                inc.read(byteBuffer);
                byteBuffer.flip();
                outc.write(byteBuffer);
                outc.force(false);
            }
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        } finally {
            try {
                if (null != inc) {
                    inc.close();
                }
                if (null != outc) {
                    outc.close();
                }
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            } catch (Exception ex) {
                LOGGER.error("error is : {}",ex.getMessage());
            }
        }
        return destination;
    }

    /**
     * rename 文件重命名
     *
     * @param filePath
     * @param from
     * @param to
     * @return
     */
    public static File rename(String filePath, String from, String to) {
        File newFile = null;
        try {
            File oldFile = new File(combainPath(filePath, from));
            newFile = new File(combainPath(filePath, to));
            rename(newFile, oldFile);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return newFile;
    }

    /**
     * rename 文件重命名
     *
     * @param to
     * @param from
     * @return
     */
    public static File rename(File from, File to) {
        try {
            String newPath = to.getPath();
            String oldPath = from.getPath();
            if (!oldPath.equals(newPath)) {
                if (!to.exists()) {
                    from.renameTo(to);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return to;
    }

    /**
     * mv 移动文件
     *
     * @param fileName
     * @param source
     * @param destination
     * @param cover
     */
    public static void mv(String fileName, String source, String destination, boolean cover) {
        try {
            if (!source.equals(destination)) {
                File oldFile = new File(combainPath(source, fileName));
                File newFile = new File(combainPath(destination, fileName));
                mv(oldFile, newFile, cover);
            }
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * mv 移动文件
     *
     * @param source
     * @param destination
     * @param cover
     */
    public static void mv(String source, String destination, boolean cover) {
        try {
            if (!source.equals(destination)) {
                File oldFile = new File(source);
                File newFile = new File(destination);
                mv(oldFile, newFile, cover);
            }
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * mv 移动文件
     *
     * @param source
     * @param destination
     * @param cover
     */
    public static void mv(File source, File destination, boolean cover) {
        try {
            if (!source.exists()) {
                throw new FileNotFoundException();
            }
            StringBuilder fileName = new StringBuilder(source.getName());
            if (!cover && source.getPath().equals(destination.getPath())) {
                if (fileName.indexOf(".") > 0) {
                    fileName.insert(fileName.lastIndexOf("."), "_副本");
                } else {
                    fileName.append("_副本");
                }
                cp(source, new File(combainPath(source.getParent(), fileName.toString())));
            } else {
                source.renameTo(destination);
            }
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * extensions 获取文件扩展名信息
     *
     * @param filePath
     * @param fileName
     * @return
     */
    private static String[] extensions(String filePath, String fileName) {
        String[] extension = {};
        try {
            String fullPath = combainPath(filePath, fileName);
            File file = new File(fullPath);
            extensions(file);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return extension;
    }

    /**
     * extensions 获取文件扩展名信息
     *
     * @param fullPath
     * @return
     */
    private static String[] extensions(String fullPath) {
        String[] extension = {};
        try {
            File file = new File(fullPath);
            extensions(file);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return extension;
    }

    /**
     * extensions 获取文件扩展名信息
     *
     * @param file
     * @return
     */
    private static String[] extensions(File file) {
        String[] extension = {};
        try {
            if (file.isFile()) {
                String fileName = file.getName();
                if (fileName.lastIndexOf(".") >= 0) {
                    int lastIndex = fileName.lastIndexOf(".");
                    extension[0] = String.valueOf(lastIndex);//扩展名的“.”的索引
                    extension[1] = fileName.substring(lastIndex + 1);//扩展名
                    extension[2] = fileName.substring(0, lastIndex);//文件名
                }
            }
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return extension;
    }


    /**
     * ls 遍历文件
     *
     * @param filePath
     * @param loop
     * @return
     */
    public static List<File> ls(String filePath, boolean loop) {
        List<File> list = new ArrayList();
        try {
            File file = new File(filePath);
            list.addAll(ls(file, loop));
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return list;
    }

    /**
     * ls 遍历文件
     *
     * @param file
     * @param loop
     * @return
     */
    public static List<File> ls(File file, boolean loop) {
        List<File> list = new ArrayList();
        try {
            list.add(file);
            if (!file.isDirectory()) {
                list.add(file);
            } else if (file.isDirectory()) {
                File[] subList = file.listFiles();
                subList = filesSort(subList, true);
                for (File subFile : subList) {
                    if (subFile.isDirectory() && loop) {
                        list.addAll(ls(subFile.getPath(), loop));
                    } else {
                        list.add(subFile);
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return list;
    }

    /**
     * filesSort 文件排序（默认升序）
     *
     * @param inFiles
     * @param asc
     * @return
     */
    private static File[] filesSort(File[] inFiles, boolean asc) {
        List<String> files = new ArrayList();
        List<String> dirs = new ArrayList();
        for (File subFile : inFiles) {
            if (subFile.isDirectory()) {
                dirs.add(subFile.getPath());
            } else if (subFile.isFile()) {
                files.add(subFile.getPath());
            }
        }
        String[] fileArray = {};
        if (files.size() > 0) {
            fileArray = list2Array(files);
            Arrays.sort(fileArray);
            if (!asc) {
                Arrays.sort(fileArray, Collections.reverseOrder());
            }
        }
        String[] dirArray = {};
        if (dirs.size() > 0) {
            dirArray = list2Array(dirs);
            Arrays.sort(dirArray);
            if (!asc) {
                Arrays.sort(dirArray, Collections.reverseOrder());
            }
        }
        return concat2FileArray(fileArray, dirArray);
    }

    /**
     * concat2FileArray 合并文件数组
     *
     * @param old1
     * @param old2
     * @return
     */
    private static File[] concat2FileArray(String[] old1, String[] old2) {
        File[] newArray = new File[old1.length + old2.length];
        for (int i = 0, n = old1.length; i < n; i++) {
            newArray[i] = new File(old1[i]);
        }
        for (int i = 0, j = old1.length, n = (old1.length + old2.length); j < n; i++, j++) {
            newArray[j] = new File(old2[i]);
        }
        return newArray;
    }

    /**
     * read 读取文本文件
     *
     * @param filePath
     * @param fileName
     * @param charset
     * @return
     */
    public static StringBuilder read(String filePath, String fileName, String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            String fullPath = combainPath(filePath, fileName);
            File file = new File(fullPath);
            sb.append(FileTools.tail(file, false, 0, charset));
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return sb;
    }

    /**
     * read 读取文本文件
     *
     * @param fullPath
     * @param charset
     * @return
     */
    public static StringBuilder read(String fullPath, String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(fullPath);
            sb.append(FileTools.tail(file, false, 0, charset));
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return sb;
    }

    /**
     * read 读取文本文件
     *
     * @param file
     * @param charset
     * @return
     */
    public static StringBuilder read(File file, String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(FileTools.tail(file, false, 0, charset));
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return sb;
    }

    /**
     * find 读取文本文件指定行
     *
     * @param filePath
     * @param fileName
     * @param line
     * @param charset
     * @return
     */
    public static StringBuilder find(String filePath, String fileName, int line, String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            String fullPath = combainPath(filePath, fileName);
            File file = new File(fullPath);
            sb.append(FileTools.tail(file, true, line, charset));
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return sb;
    }

    /**
     * find 读取文本文件指定行
     *
     * @param fullPath
     * @param line
     * @param charset
     * @return
     */
    public static StringBuilder find(String fullPath, int line, String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(fullPath);
            sb.append(FileTools.tail(file, true, line, charset));
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return sb;
    }

    /**
     * find 读取文本文件指定行
     *
     * @param file
     * @param line
     * @param charset
     * @return
     */
    public static StringBuilder find(File file, int line, String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(FileTools.tail(file, true, line, charset));
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return sb;
    }

    /**
     * tail 读取文本文件
     *
     * @param filePath
     * @param fileName
     * @param charset
     * @param find
     * @param line
     * @return
     */
    public static StringBuilder tail(String filePath, String fileName, boolean find, int line, String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            String fullPath = combainPath(filePath, fileName);
            File file = new File(fullPath);
            sb.append(FileTools.tail(file, find, line, charset));
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return sb;
    }

    /**
     * tail 读取文本文件
     *
     * @param fullPath
     * @param charset
     * @param find
     * @param line
     * @return
     */
    public static StringBuilder tail(String fullPath, boolean find, int line, String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(fullPath);
            sb.append(FileTools.tail(file, find, line, charset));
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return sb;
    }

    /**
     * tail 读取文本文件
     *
     * @param file
     * @param charset
     * @param find
     * @param line
     * @return
     */
    public static StringBuilder tail(File file, boolean find, int line, String charset) {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferReader = null;
        if (null == charset || "".equals(charset)) {
            charset = "UTF-8";
        }
        try {
            if (!file.exists() || file.isDirectory()) {
                throw new FileNotFoundException();
            }
            String fullPath = file.getPath();
            bufferReader = new BufferedReader(new InputStreamReader(new FileInputStream(fullPath), charset));
            String temp;
            for (int i = 0; (temp = bufferReader.readLine()) != null; i++) {
                if (!find || line == i) {
                    sb.append(temp);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        } finally {
            if (null != bufferReader) {
                try {
                    bufferReader.close();
                } catch (IOException ex) {
                    LOGGER.error("error is : {}",ex.getMessage());
                }
            }
        }
        return sb;
    }

    /**
     * sed 读取文本文件
     *
     * @param filePath
     * @param fileName
     * @param charset
     * @return
     */
    public static List<String> sed(String filePath, String fileName, String charset) {
        List<String> list = new ArrayList();
        try {
            String fullPath = combainPath(filePath, fileName);
            File file = new File(fullPath);
            list.addAll(FileTools.sed(file, charset));
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return list;
    }

    /**
     * sed 读取文本文件
     *
     * @param fullPath
     * @param charset
     * @return
     */
    public static List<String> sed(String fullPath, String charset) {
        List<String> list = new ArrayList();
        try {
            File file = new File(fullPath);
            list.addAll(FileTools.sed(file, charset));
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return list;
    }

    /**
     * sed 读取文本文件
     *
     * @param file
     * @param charset
     * @return
     */
    public static List<String> sed(File file, String charset) {
        List<String> list = new ArrayList();
        BufferedReader bufferReader = null;
        if (null == charset || "".equals(charset)) {
            charset = "UTF-8";
        }
        try {
            if (!file.exists() || file.isDirectory()) {
                throw new FileNotFoundException();
            }
            String fullPath = file.getPath();
            bufferReader = new BufferedReader(new InputStreamReader(new FileInputStream(fullPath), charset));
            String temp;
            for (int i = 0; (temp = bufferReader.readLine()) != null; i++) {
                list.add(temp);
            }
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        } finally {
            if (null != bufferReader) {
                try {
                    bufferReader.close();
                } catch (IOException ex) {
                    LOGGER.error("error is : {}",ex.getMessage());
                }
            }
        }
        return list;
    }

    /**
     * cat 读取文本文件
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public static byte[] cat(String filePath, String fileName) {
        byte[] output = {};
        try {
            String fullPath = combainPath(filePath, fileName);
            File file = new File(fullPath);
            output = FileTools.cat(file);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return output;
    }

    /**
     * cat 读取文本文件
     *
     * @param fullPath
     * @return
     */
    public static byte[] cat(String fullPath) {
        byte[] output = {};
        try {
            File file = new File(fullPath);
            output = FileTools.cat(file);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return output;
    }

    /**
     * cat 读取文本文件
     *
     * @param file
     * @return
     */
    public static byte[] cat(File file) {
        InputStream in = null;
        byte[] output = {};
        try {
            if (!file.exists() || file.isDirectory()) {
                throw new FileNotFoundException();
            }
            String fullPath = file.getPath();
            long length = du(file, false);
            long _2M = 2097152;
            byte[] bytes = new byte[(int) length];
            in = new FileInputStream(fullPath);
            for (int count = 0; count != -1;) {
                if (length > 16 * _2M) {
                    length = 4 * _2M;
                } else if (length > 8 * _2M) {
                    length = 2 * _2M;
                } else if (length > 4 * _2M) {
                    length = _2M;
                } else if (length > 2 * _2M) {
                    length = _2M / 2;
                } else if (length > _2M) {
                    length = _2M / 4;
                } else {
                    length = 4096;
                }
                bytes = new byte[(int) length];
                count = in.read(bytes);
                output = concatArray(bytes, output);
                length = in.available();
            }
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (Exception ex) {
                    LOGGER.error("error is : {}",ex.getMessage());
                }
            }
        }
        return output;
    }

    /**
     * 合并数组
     *
     * @param old1
     * @param old2
     * @return
     */
    private static byte[] concatArray(byte[] old1, byte[] old2) {
        byte[] newArray = new byte[old1.length + old2.length];
        System.arraycopy(old1, 0, newArray, 0, old1.length);
        System.arraycopy(old2, 0, newArray, old1.length, old2.length);
        return newArray;
    }

    /**
     * dd 写入文件fullPath内容content
     *
     * @param filePath
     * @param fileName
     * @param content
     * @param isAppend
     */
    public static void dd(String filePath, String fileName, byte[] content, boolean isAppend) {
        try {
            String fullPath = combainPath(filePath, fileName);
            File file = new File(fullPath);
            FileTools.dd(file, content, isAppend);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * dd 写入文件fullPath内容content
     *
     * @param fullPath
     * @param content
     * @param isAppend
     */
    public static void dd(String fullPath, byte[] content, boolean isAppend) {
        try {
            File file = new File(fullPath);
            FileTools.dd(file, content, isAppend);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    public static void base64ToImage(String filePath,String fileName,String base64,boolean isAppend) throws IOException {
        try {
            byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(base64);
            for (int i = 0; i < bytes.length; ++i) {
                // 调整异常数据
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }
            FileTools.dd(filePath, fileName, bytes, isAppend);
        }catch (Exception ex){
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * 将InputStream写入本地文件
     * @param fileName 写入本地目录
     * @param input 输入流
     * @throws IOException IOException
     */
    public static void writeToLocal(String path,String fileName, InputStream input) throws IOException {
        String fullPath = combainPath(path, fileName);
        File file = new File(fullPath);
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream(file);
        while ((index = input.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        input.close();
        downloadFile.close();
    }


    /**
     * file转Base64
     * @param file
     * @return
     */
    public static String FileToBase64(MultipartFile file){
        try {
            return org.apache.commons.codec.binary.Base64.encodeBase64String(file.getBytes());
        }catch (Exception ex){
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return null;
    }

    /**
     * 文件转base64
     * @param path
     * @return
     */
    public static String FileToBase64(String path){
        try {
            File file = new File(path);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[(int)file.length()];
            inputStream.read(buffer);
            inputStream.close();
            return org.apache.commons.codec.binary.Base64.encodeBase64String(buffer);
        }catch (Exception ex){
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return null;
    }

    /**
     * dd 写入文件fullPath内容content
     *
     * @param file
     * @param content
     * @param isAppend
     */
    public static void dd(File file, byte[] content, boolean isAppend) {
        FileOutputStream fileOutputStream = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file, isAppend);
            fileOutputStream.write(content);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        } finally {
            try {
                if (null != fileOutputStream) {
                    fileOutputStream.close();
                }
            } catch (IOException ex) {
                LOGGER.error("error is : {}",ex.getMessage());
            }
        }
    }

    /**
     * write 写文件内容content到文件fullPath
     *
     * @param filePath
     * @param fileName
     * @param content
     */
    public static void write(String filePath, String fileName, String content) {
        try {
            String fullPath = combainPath(filePath, fileName);
            File file = new File(fullPath);
            FileTools.write(file, content, true);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * write 写文件内容content到文件fullPath
     *
     * @param fullPath
     * @param content
     */
    public static void write(String fullPath, String content) {
        try {
            File file = new File(fullPath);
            FileTools.write(file, content, true);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * write 写文件内容content到文件fullPath
     *
     * @param file
     * @param content
     */
    public static void write(File file, String content) {
        try {
            FileTools.write(file, content, true);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * write 写（追加）文件内容content到文件fullPath
     *
     * @param filePath
     * @param fileName
     * @param content
     * @param isAppend
     */
    public static void write(String filePath, String fileName, String content, boolean isAppend) {
        try {
            String fullPath = combainPath(filePath, fileName);
            File file = new File(fullPath);
            FileTools.write(file, content, isAppend);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * write 写（追加）文件内容content到文件fullPath
     *
     * @param fullPath
     * @param content
     * @param isAppend
     */
    public static void write(String fullPath, String content, boolean isAppend) {
        try {
            File file = new File(fullPath);
            FileTools.write(file, content, isAppend);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * write 写（追加）文件内容content到文件fullPath
     *
     * @param file
     * @param content
     * @param isAppend
     */
    public static void write(File file, String content, boolean isAppend) {
        FileWriter fileWriter = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getPath(), isAppend);
            fileWriter.write(content);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        } finally {
            if (null != fileWriter) {
                try {
                    fileWriter.close();
                } catch (IOException ex) {
                    LOGGER.error("error is : {}",ex.getMessage());
                }
            }
        }
    }

    /**
     * tail 添加文件内容content到文件的index位置
     *
     * @param filePath
     * @param fileName
     * @param content
     * @param index
     */
    public static void tail(String filePath, String fileName, String content, long index) {
        try {
            String fullPath = combainPath(filePath, fileName);
            File file = new File(fullPath);
            FileTools.tail(file, content, index);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * tail 添加文件内容content到文件的index位置
     *
     * @param fullPath
     * @param content
     * @param index
     */
    public static void tail(String fullPath, String content, long index) {
        try {
            File file = new File(fullPath);
            FileTools.tail(file, content, index);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * tail 添加文件内容content到文件的index位置
     *
     * @param file
     * @param content
     * @param index
     */
    public static void tail(File file, String content, long index) {
        RandomAccessFile randomAccessFile = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            randomAccessFile = new RandomAccessFile(file.getPath(), "rw");
            randomAccessFile.seek(index);
            randomAccessFile.writeBytes(content);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        } finally {
            if (null != randomAccessFile) {
                try {
                    randomAccessFile.close();
                } catch (Exception ex) {
                    LOGGER.error("error is : {}",ex.getMessage());
                }
            }
        }
    }

    /**
     * mkdir 创建目录
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public static File mkdir(String filePath, String fileName) {
        File file = null;
        try {
            String fullPath = combainPath(filePath, fileName);
            file = new File(fullPath);
            file = mkdir(file);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return file;
    }

    /**
     * mkdir 创建目录
     *
     * @param fullPath
     * @return
     */
    public static File mkdir(String fullPath) {
        File file = null;
        try {
            file = new File(fullPath);
            file = mkdir(file);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return file;
    }

    /**
     * mkdir 创建目录
     *
     * @param file
     * @return
     */
    public static File mkdir(File file) {
        try {
            if (!file.exists()) {
                file.mkdir();//如果文件夹不存在则创建
            }
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return file;
    }

    /**
     * touch 创建文件
     *
     * @param filePath
     * @param fileName
     */
    public static void touch(String filePath, String fileName) {
        try {
            String fullPath = combainPath(filePath, fileName);
            File file = new File(fullPath);
            touch(file);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * touch 创建文件
     *
     * @param fullPath
     */
    public static void touch(String fullPath) {
        try {
            File file = new File(fullPath);
            touch(file);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * touch 创建文件
     *
     * @param file
     */
    public static void touch(File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();//如果文件不存在则创建
            }
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * rm 删除文件
     *
     * @param filePath
     * @param fileName
     */
    public static void rm(String filePath, String fileName) {
        try {
            String fullPath = combainPath(filePath, fileName);
            File file = new File(fullPath);
            rm(file);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * rm 删除文件
     *
     * @param fullPath
     */
    public static void rm(String fullPath) {
        try {
            File file = new File(fullPath);
            rm(file);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * rm 删除文件
     *
     * @param file
     */
    public static void rm(File file) {
        try {
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
            if (file.isFile()) {
                file.delete();
            }
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * rmdir 删除目录
     *
     * @param filePath
     * @param fileName
     * @param loop
     */
    public static void rmdir(String filePath, String fileName, boolean loop) {
        try {
            String fullPath = combainPath(filePath, fileName);
            File dir = new File(fullPath);
            rmdir(dir, loop);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * rmdir 删除目录
     *
     * @param fullPath
     * @param loop
     */
    public static void rmdir(String fullPath, boolean loop) {
        try {
            File dir = new File(fullPath);
            rmdir(dir, loop);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * rmdir 删除目录
     *
     * @param dir
     * @param loop
     */
    public static void rmdir(File dir, boolean loop) {
        try {
            if (!dir.exists()) {
                throw new FileNotFoundException();
            }
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                int length = files.length;
                for (int i = 0; i < length && loop; i++) {
                    if (files[i].isDirectory()) {
                        rmdir(files[i], loop);
                    } else {
                        rm(files[i]);
                    }
                }
                if (loop || length == 0) {
                    dir.delete();
                }
            }
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
    }

    /**
     * du 获取文件实际大小
     *
     * @param filePath
     * @param fileName
     * @param loop
     * @return
     */
    public static long du(String filePath, String fileName, boolean loop) {
        long size = 0;
        try {
            String fullPath = combainPath(filePath, fileName);
            File file = new File(fullPath);
            size = du(file, loop);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return size;
    }

    /**
     * du 获取文件实际大小
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public static long du(String filePath, String fileName) {
        long size = 0;
        try {
            String fullPath = combainPath(filePath, fileName);
            File file = new File(fullPath);
            size = du(file, false);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return size;
    }

    /**
     * du 获取文件实际大小
     *
     * @param fullPath
     * @return
     */
    public static long du(String fullPath) {
        long size = 0;
        try {
            File file = new File(fullPath);
            size = du(file, false);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return size;
    }

    /**
     * du 获取文件实际大小
     *
     * @param file
     * @return
     */
    public static long du(File file) {
        long size = 0;
        try {
            size = du(file, false);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return size;
    }

    /**
     * du 获取文件实际大小
     *
     * @param fullPath
     * @param loop
     * @return
     */
    public static long du(String fullPath, boolean loop) {
        long size = 0;
        try {
            File file = new File(fullPath);
            size = du(file, loop);
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        }
        return size;
    }

    /**
     * du 获取文件实际大小
     *
     * @param file
     * @param loop
     * @return
     */
    public static long du(File file, boolean loop) {
        FileChannel fileChannel = null;
        long size = 0;
        try {
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
            if (file.isFile()) {
                FileInputStream fis = new FileInputStream(file);
                fileChannel = fis.getChannel();
                size = fileChannel.size();
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                int length = files.length;
                for (int i = 0; i < length && loop; i++) {
                    if (files[i].isDirectory()) {
                        du(files[i], loop);
                    } else {
                        size += du(files[i], false);
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("error is : {}",ex.getMessage());
        } finally {
            if (null != fileChannel) {
                try {
                    fileChannel.close();
                } catch (Exception ex) {
                    LOGGER.error("error is : {}",ex.getMessage());
                }
            }
        }
        return size;
    }

    /**
     * 压缩整个文件夹中的所有文件，生成指定名称的zip压缩包
     * @param filepath 文件所在目录
     * @param zippath 压缩后zip文件名称
     * @param dirFlag zip文件中第一层是否包含一级目录，true包含；false没有
     * 2015年6月9日
     */
    public static void zipMultiFile(String filepath ,String zippath, boolean dirFlag) {
        File file = new File(filepath);
        File zipFile = new File(zippath);
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile))){
            // 要被压缩的文件夹
            if(file.isDirectory()){
                File[] files = file.listFiles();
                for(File fileSec:files){
                    if(dirFlag){
                        recursionZip(zipOut, fileSec, file.getName() + File.separator);
                    }else{
                        recursionZip(zipOut, fileSec, "");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //压缩完成之后删除临时下载文件
            FileTools.rmdir(file,true);
        }
    }

    /**
     * 压缩文件的具体实现
     * @param zipOut
     * @param file
     * @param baseDir
     * @throws Exception
     */
    private static void recursionZip(ZipOutputStream zipOut, File file, String baseDir) throws Exception{
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File fileSec:files){
                recursionZip(zipOut, fileSec, baseDir + file.getName() + File.separator);
            }
        }else{
            byte[] buf = new byte[1024];
            InputStream input = new FileInputStream(file);
            zipOut.putNextEntry(new ZipEntry(baseDir + file.getName()));
            int len;
            while((len = input.read(buf)) != -1){
                zipOut.write(buf, 0, len);
            }
            input.close();
        }
    }
}
