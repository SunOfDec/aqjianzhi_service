package com.jz.aqjianzhi.ws_service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jz.aqjianzhi.utils.R;
import com.jz.aqjianzhi.ws_service.entity.AqUserChat;
import com.jz.aqjianzhi.ws_service.entity.vo.MessageVo;
import com.jz.aqjianzhi.ws_service.entity.vo.UserVo;
import com.jz.aqjianzhi.ws_service.service.AqUserChatService;
import com.jz.aqjianzhi.ws_service.service.AqUserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xyk
 * @since 2021-04-03
 */
@RestController
@RequestMapping("/ws_service/aq-user-chat")
@CrossOrigin
public class AqUserChatController {

    @Autowired
    private AqUserChatService chatService;

    @Autowired
    private AqUserContactService contactService;


    @GetMapping("/getMessages/{sessionId}")
    public R getMessage(@PathVariable String sessionId) {
        // 根据sessionId获取数据库中的聊天信息
        QueryWrapper<AqUserChat> wrapper = new QueryWrapper<>();
        wrapper.eq("session_id", sessionId).orderByAsc("c_level");

        List<AqUserChat> userChatMessages = chatService.list(wrapper);
        // 获取开始聊天的时间
//        Date firstChatTime = chatService.getFirstChatTime(sessionId);

        // 获取接收者用户信息
        // 获取
        List<MessageVo> messages = new ArrayList<>();
        for (AqUserChat userChatMessage : userChatMessages) {
            MessageVo messageVo = new MessageVo();
            messageVo.set_id(userChatMessage.getCId());
            messageVo.setContent(userChatMessage.getCMessage());
            String sendUserId = userChatMessage.getCSendUserId();
            messageVo.setSenderId(sendUserId);
            // 根据发送者id查询到发送者的信息
            UserVo userMessage = contactService.getUserMessage(sendUserId);
            messageVo.setReceiverId(userMessage.getUserId());
            // 设置用户名和头像
            messageVo.setUsername(userMessage.getUserName());
            messageVo.setAvatar(userMessage.getIcon());
//            messageVo.setDate(firstChatTime);
            messageVo.setTimestamp(userChatMessage.getCreateTime());

            messageVo.setSessionId(sessionId);
            messageVo.setCLevel(userChatMessage.getCLevel());

            messageVo.setSeen(true);

            messages.add(messageVo);
        }

        return R.ok().data("messages", messages);
    }

    @PutMapping("/readChatMsg/{sessionId}/{uId}")
    public R readChatMsg(@PathVariable String sessionId,
                         @PathVariable String uId) {
        QueryWrapper<AqUserChat> wrapper = new QueryWrapper<>();
        wrapper.eq("session_id", sessionId).eq("is_read", 0)
                .eq("c_receive_user_id", uId);
        AqUserChat userChat = new AqUserChat();
        userChat.setRead(true);
        chatService.update(userChat, wrapper);
        return R.ok();
    }

/*    @PutMapping("/recordUnreadChatMsg/{sessionId}/{level}")
    public R recordUnreadChatMsg(@PathVariable String sessionId,
                                 @PathVariable Long level) {
        QueryWrapper<AqUserChat> wrapper = new QueryWrapper<>();
        wrapper.eq("session_id", sessionId).eq("c_level", level);

        AqUserChat userChat = new AqUserChat();
        userChat.setRead(false);
        chatService.update(userChat, wrapper);
        return R.ok();
    }*/

}

