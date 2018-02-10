package com.servicecomb.common.utils;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * http请求工具类
 */
public class HttpClientUtil {
    private static Logger logger = Logger.getLogger(HttpClientUtil.class);

    private static PoolingHttpClientConnectionManager connectionManager = null;
    private static HttpClientBuilder httpBulder = null;
    private static RequestConfig requestConfig = null;

    private static int MAXCONNECTION = 10;

    private static int DEFAULTMAXCONNECTION = 5;

    private static String IP = "api.weixin.qq.com";
    private static int PORT = 80;

    static {
        //设置http的状态参数
        requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();

        HttpHost target = new HttpHost(IP, PORT);
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAXCONNECTION);
        connectionManager.setDefaultMaxPerRoute(DEFAULTMAXCONNECTION);
        connectionManager.setMaxPerRoute(new HttpRoute(target), 20);
        httpBulder = HttpClients.custom();
        httpBulder.setConnectionManager(connectionManager);
    }

    public static CloseableHttpClient getConnection() {
        CloseableHttpClient httpClient = httpBulder.build();
        httpClient = httpBulder.build();
        return httpClient;
    }


    public static HttpUriRequest getRequestMethod(Map<String, Object> map, String url, String method) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(map != null){
            Set<Map.Entry<String, Object>> entrySet = map.entrySet();
            for (Map.Entry<String, Object> e : entrySet) {
                String name = e.getKey();
                String value = String.valueOf(e.getValue());
                NameValuePair pair = new BasicNameValuePair(name,value);
                params.add(pair);
            }
        }
        HttpUriRequest reqMethod = null;
        if ("POST".equals(method)) {
            reqMethod = RequestBuilder.post().setUri(url)
                    .addParameters(params.toArray(new BasicNameValuePair[params.size()]))
                    .setConfig(requestConfig).build();
        } else if ("GET".equals(method)) {
            reqMethod = RequestBuilder.get().setUri(url)
                    .addParameters(params.toArray(new BasicNameValuePair[params.size()]))
                    .setConfig(requestConfig).build();
        }
        return reqMethod;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String doPost(String url, String param)
    {
        String result = "";
        HttpURLConnection connection = null;
        OutputStream os = null;
        InputStream is = null;
        try
        {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.connect();
            os = connection.getOutputStream();
            if (param != null && !param.trim().equals(""))
            {
                // 获取URLConnection对象对应的输出流
                logger.info("param is :"+param);
                os.write(param.getBytes("UTF-8"));
                // 发送请求参数
                os.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            is = connection.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            result = new String(jsonBytes,"UTF-8");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally
        {
            try
            {
                if (os != null)
                {
                    os.close();
                }
                if (is != null)
                {
                    is.close();
                }
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
            connection.disconnect();
        }
        return result;
    }
}
