package com.sample.servicecomb.consumer.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * WebSocket服务端示例
 * @author wallimn，http://wallimn.iteye.com
 *
 */
@ServerEndpoint(value = "/ws/asset")
@Component
public class WebSocketServer {

    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String sid = "";

    /**
     *
     * @Description：连接建立成功调用的方法
     * @Date：2018/12/13 下午3:16
     * @Author：Mujiutian
     * @UpdateRemark:
     * @Version:1.0
     *
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("sid")String sid) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        log.info("有新窗口开始监听："+sid+"有新连接加入！当前在线人数为" + getOnlineCount());
        this.sid = sid;

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     *
     * @Description：连接关闭调用的方法
     * @Date：2018/12/13 下午3:15
     * @Author：Mujiutian
     * @UpdateRemark:
     * @Version:1.0
     *
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     *
     * @Description：收到客户端消息后调用的方法,message:客户端发送过来的消息
     * @Date：2018/12/13 下午3:16
     * @Author：ChengJian
     * @UpdateRemark:
     * @Version:1.0
     *
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);

        //群发消息
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }


    /**
     *
     * @Description：实现服务器主动推送
     * @Date：2018/12/13 下午3:17
     * @Author：ChengJian
     * @UpdateRemark:
     * @Version:1.0
     *
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     *
     * @Description：群发自定义消息
     * @Date：2018/12/13 下午3:18
     * @Author：Mujiutian
     * @UpdateRemark:
     * @Version:1.0
     *
     */
    public static void sendInfo(String message, @PathParam("sid")String sid) throws IOException {
        log.info("推送消息到窗口"+sid+",推送消息内容:"+message);
        for (WebSocketServer item : webSocketSet) {
            try {
                //设定只能推送这个sid，为null则全部推送
                if (sid == null){
                    item.sendMessage(message);
                }else if (item.sid.equals(sid)){
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}