package com.jz.aqjianzhi.ws_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jz.aqjianzhi.ws_service.entity.testChat.Message;
import com.jz.aqjianzhi.ws_service.entity.testChat.MessageUtils;
import com.jz.aqjianzhi.ws_service.entity.vo.MessageVo;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@ServerEndpoint("/websocket/{uId}")
public class WebSocketController02Test {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static Map<String, WebSocketController02Test> webSocketSet = new ConcurrentHashMap<String, WebSocketController02Test>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session WebSocketsession;
    //当前发消息的人员编号
    private String uid = "";

    // 声明一个HTTPSession对象，之前在HTTPSession中存储了用户名
//    private HttpSession httpSession;

    /**
     * 连接建立成功调用的方法
     * <p>
     * session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value = "uId") String param, Session WebSocketsession, EndpointConfig endpointConfig) {
        this.uid = param;//接收到发送消息的人员编号
        this.WebSocketsession = WebSocketsession;  // 局部session赋值给成员session

        // 获取httpsession对象 以下两行相当于 uId = param
        /*HttpSession httpSession = (HttpSession) endpointConfig.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;*/


        webSocketSet.put(param, this);//加入map中


        System.out.println(webSocketSet);

        // 获取所有在线用户账号，推送系统消息
        String message = MessageUtils.getMessage(true, null, getUserId());
        broadcastAllUsers(message);

        addOnlineCount();     //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 获取在线账号
     */
    private Set<String> getUserId() {
        return webSocketSet.keySet();
    }

    /**
     * 推送广播消息
     */
    private void broadcastAllUsers(String message) {
        // 拿到所有的用户名
        Set<String> strings = webSocketSet.keySet();
        /*for (String userId : strings) {
            WebSocketController02Test chatEndpoint = webSocketSet.get(userId);
            try {
                chatEndpoint.WebSocketsession.getBasicRemote().sendText("测试广播发送！");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }*/

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (!"".equals(uid)) {
            webSocketSet.remove(uid); //从set中删除
            subOnlineCount();     //在线数减1
            System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        }
    }

    @OnMessage
    public void onMessage(String msg, Session session) throws IOException {
        /*System.out.println(msg);
        JSONObject jsonMsg = JSONObject.parseObject(msg);
        System.out.println(jsonMsg);*/
        System.out.println(msg);
        JSONObject jsonMsg = JSONObject.parseObject(msg);
        MessageVo messageVo = jsonMsg.toJavaObject(MessageVo.class);
        System.out.println(messageVo);
        System.out.println(messageVo.getReceiverId());
        String id = messageVo.getReceiverId();
        webSocketSet.get(id).WebSocketsession.getBasicRemote().sendText(msg);
        /*try {
            ObjectMapper mapper = new ObjectMapper();
            Message message = mapper.readValue(msg, Message.class);
            System.out.println(message);

            String toName = message.getToName();
            String data = message.getMessage();
            String res = MessageUtils.getMessage(false, this.uid, data);
            // 发送数据
            webSocketSet.get(toName).WebSocketsession.getBasicRemote().sendText(res);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }


    public static synchronized void addOnlineCount() {
        WebSocketController02Test.onlineCount++;
    }


    public static synchronized void subOnlineCount() {
        WebSocketController02Test.onlineCount--;
    }
}
