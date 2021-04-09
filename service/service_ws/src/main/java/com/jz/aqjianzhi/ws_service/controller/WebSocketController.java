package com.jz.aqjianzhi.ws_service.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jz.aqjianzhi.ws_service.entity.AqUserContact;
import com.jz.aqjianzhi.ws_service.entity.MessageType;
import com.jz.aqjianzhi.ws_service.entity.ResultMessage;
import com.jz.aqjianzhi.ws_service.entity.vo.MessageVo;
import com.jz.aqjianzhi.ws_service.service.AqUserChatService;
import com.jz.aqjianzhi.ws_service.service.AqUserContactService;
import com.jz.aqjianzhi.ws_service.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@ServerEndpoint("/websocket/{uId}")
public class WebSocketController {

    // 这里使用静态，让 service 属于类
    private static AqUserChatService chatService;
    private static AqUserContactService contactService;

    @Autowired
    public void setChatService(AqUserChatService service) {
        WebSocketController.chatService = service;
    }
    @Autowired
    public void setContactService(AqUserContactService service) {
        WebSocketController.contactService = service;
    }

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static Map<String, WebSocketController> webSocketSet = new ConcurrentHashMap<String, WebSocketController>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session webSocketSession;
    //当前发消息的用户id
    private String uid = "";

    /**
     * 连接建立成功调用的方法
     * <p>
     * session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value = "uId") String param, Session WebSocketsession, EndpointConfig endpointConfig) {
        this.uid = param;//接收到发送消息的人员编号
        this.webSocketSession = WebSocketsession;  // 局部session赋值给成员session

        webSocketSet.put(param, this);//加入map中

        // 获取所有在线用户账号，推送系统消息
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setType(MessageType.SYSTEM_MESSAGE_ONLINE);
        resultMessage.setMessage(MessageUtils.ObjectToJSONString(getUserId()));
        String sysMsgForOnlineUsers = MessageUtils.ObjectToJSONString(resultMessage);
        broadcastAllUsers(sysMsgForOnlineUsers);

        System.out.println("测试：有新连接加入！当前在线人数为" + MessageUtils.ObjectToJSONString(getUserId()));
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (!"".equals(uid)) {
            webSocketSet.remove(uid); //从set中删除
            System.out.println("测试：有一连接关闭！当前在线人数为" + MessageUtils.ObjectToJSONString(getUserId()));

            // 获取所有在线用户账号，推送系统消息
            ResultMessage resultMessage = new ResultMessage();
            resultMessage.setType(MessageType.SYSTEM_MESSAGE_ONLINE);
            resultMessage.setMessage(MessageUtils.ObjectToJSONString(getUserId()));
            String sysMsgForOnlineUsers = MessageUtils.ObjectToJSONString(resultMessage);
            broadcastAllUsers(sysMsgForOnlineUsers);
        }
    }

    @OnMessage
    public void onMessage(String msg, Session session) {
        // 将客户端发送来的消息转换为Java对象
        ResultMessage resultMessage = MessageUtils.JSONStringToObject(msg, ResultMessage.class);

        // 如果消息类型为聊天信息
        if (resultMessage.getType().equals(MessageType.USER_CHAT_MESSAGE)) {
            String userChatMsg = resultMessage.getMessage();
            // 保存聊天记录到数据库
            this.saveChatMsgToDB(userChatMsg);
            this.sendMsgToUser(userChatMsg);
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
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
        for (String userId : strings) {
            WebSocketController chatEndpoint = webSocketSet.get(userId);
            try {
                chatEndpoint.webSocketSession.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 当用户上线时，查询联系过该用户的其它用户，把上线通知发送给这些用户
     */

    private void saveChatMsgToDB(String msg) {
        MessageVo messageVo = MessageUtils.JSONStringToObject(msg, MessageVo.class);
        String id1 = messageVo.get_id();
        String sessionId = messageVo.getSessionId();
        Long cLevel = messageVo.getCLevel();
        String content = messageVo.getContent();
        Date timestamp = messageVo.getTimestamp();
        String senderId = messageVo.getSenderId();
        String receiverId = messageVo.getReceiverId();

        System.out.println("mysql保存数据");
        System.out.println(content);

        chatService.saveChatMessage(id1, sessionId, content, senderId, receiverId, cLevel, timestamp);
    }

    private void sendMsgToUser(String msg) {
        MessageVo messageVo = MessageUtils.JSONStringToObject(msg, MessageVo.class);
        // 设置统一消息返回格式
        ResultMessage resultMessage = new ResultMessage();
        // 获取接收者id
        String id = messageVo.getReceiverId();

        try {
            // 如果接收者是否在线
            if (webSocketSet.get(id) != null) {
                resultMessage.setType(MessageType.USER_CHAT_MESSAGE);
                resultMessage.setMessage(msg);
                String res = MessageUtils.ObjectToJSONStringWithDateFormat(resultMessage);
                webSocketSet.get(id).webSocketSession.getBasicRemote().sendText(res);
            } else {
                MessageVo messageVo1 = new MessageVo();
                messageVo1.setSystem(true);  // 仅聊天系统通知信息
                String offlineChatMsg = MessageUtils.ObjectToJSONStringWithDateFormat(messageVo1);
                resultMessage.setType(MessageType.USER_CHAT_MESSAGE);
                resultMessage.setMessage(offlineChatMsg);
                String res = MessageUtils.ObjectToJSONStringWithDateFormat(resultMessage);
                webSocketSet.get(this.uid).webSocketSession.getBasicRemote().sendText(res);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 手动调用发送消息
     * @param message
     */
    public void sendMessage(String message) {
        try {
            this.webSocketSession.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前在线的用户
     */
    /*public static synchronized Map<String, WebSocketController> getOnlineUsers() {
        return WebSocketController.webSocketSet;
    }*/
}
