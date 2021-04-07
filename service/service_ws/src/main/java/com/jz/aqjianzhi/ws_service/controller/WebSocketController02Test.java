package com.jz.aqjianzhi.ws_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jz.aqjianzhi.ws_service.entity.testChat.Message;
import com.jz.aqjianzhi.ws_service.entity.testChat.MessageUtils;
import com.jz.aqjianzhi.ws_service.entity.testChat.ResultMessage;
import com.jz.aqjianzhi.ws_service.entity.vo.MessageVo;
import com.jz.aqjianzhi.ws_service.service.AqUserChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@ServerEndpoint("/websocket/{uId}")
public class WebSocketController02Test {

    // 这里使用静态，让 service 属于类
    private static AqUserChatService chatService;
    @Autowired
    public void setChatService(AqUserChatService service) {
        WebSocketController02Test.chatService = service;
    }

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
    public void onMessage(String msg, Session session) {
        // 将客户端发送来的消息转换为Java对象
        JSONObject jsonMsg = JSONObject.parseObject(msg);
        MessageVo messageVo = jsonMsg.toJavaObject(MessageVo.class);
        // 设置统一消息返回格式
        ResultMessage resultMessage = new ResultMessage();
        // 获取接收者id
        String id = messageVo.getReceiverId();

        String id1 = messageVo.get_id();
        String sessionId = messageVo.getSessionId();
        Long cLevel = messageVo.getCLevel();
        String content = messageVo.getContent();
        Date timestamp = messageVo.getTimestamp();
        String senderId = messageVo.getSenderId();
        String receiverId = messageVo.getReceiverId();

        int i = chatService.saveChatMessage(id1, sessionId, content, senderId, receiverId, cLevel, timestamp);
        System.out.println("添加数据.................");
        System.out.println(i);


        try {
            if (webSocketSet.get(id) != null) {  // 判断接收者是否在线
                resultMessage.setFromName(this.uid);
                resultMessage.setMessage(msg);
                resultMessage.setSystem(false);  // 系统推送通知
                String res = JSONObject.toJSONStringWithDateFormat(resultMessage, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);

                webSocketSet.get(id).WebSocketsession.getBasicRemote().sendText(res);

            } else {




                MessageVo messageVo1 = new MessageVo();
                messageVo1.setSystem(true);
                String m = JSONObject.toJSONStringWithDateFormat(messageVo1, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);

                resultMessage.setFromName("-1");  // -1代表系统发送
                resultMessage.setMessage(m);
                resultMessage.setSystem(false);  // 系统推送通知
                String res = JSONObject.toJSONStringWithDateFormat(resultMessage, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
                webSocketSet.get(this.uid).WebSocketsession.getBasicRemote().sendText(res);

            }




        } catch (IOException e) {
            e.printStackTrace();
        }
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
