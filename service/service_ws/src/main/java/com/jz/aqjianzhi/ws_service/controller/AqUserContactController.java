package com.jz.aqjianzhi.ws_service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jz.aqjianzhi.utils.R;
import com.jz.aqjianzhi.ws_service.entity.AqUserChat;
import com.jz.aqjianzhi.ws_service.entity.AqUserContact;
import com.jz.aqjianzhi.ws_service.entity.vo.*;
import com.jz.aqjianzhi.ws_service.service.AqUserChatService;
import com.jz.aqjianzhi.ws_service.service.AqUserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xyk
 * @since 2021-04-03
 */
@RestController
@RequestMapping("/ws_service")
@CrossOrigin
public class AqUserContactController {

    @Autowired
    private AqUserContactService userContactService;

    @Autowired
    private AqUserChatService userChatService;
    
    @PostMapping("/addContact")
    public R addContact(@RequestBody AqUserContact userContact) {
        String contactId = userContact.getUserContactId();
        String userId = userContact.getUserId();

        QueryWrapper<AqUserContact> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("user_contact_id", contactId);
        int count = userContactService.count(wrapper);
        if (count > 0) {
            // 如果数据存在返回错误
            return R.error();
        } else {
            // 查询对方是否添加过我们
            QueryWrapper<AqUserContact> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("user_id", contactId).eq("user_contact_id", userId);
            AqUserContact contact = userContactService.getOne(wrapper2);
            // 如果对方添加过我们，获取会话id
            String sessionId;
            if (null != contact) {
                sessionId = contact.getSessionId();

            } else {  // 如果对方没有添加过我们，生成一个唯一值作为新的一组会话
                sessionId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            }
            userContact.setSessionId(sessionId);
            boolean save = userContactService.save(userContact);
            return save ? R.ok() : R.error();
        }
    }
    

    @GetMapping("/getAllContactByUId/{uId}")
    public R getAllContactByUId(@PathVariable String uId) {

        List<ContactVo> allContact = userContactService.getAllContact(uId);
        UserVo userMessage = userContactService.getUserMessage(uId);

        List<ChatRoomVo> chatRoomList = new ArrayList<>();
        int n = 0;
        for (ContactVo contact : allContact) {
            ChatRoomVo chatRoomVo = new ChatRoomVo();
            /*聊天室基本信息*/
            String sessionId = contact.getSessionId();
            chatRoomVo.setRoomId(sessionId);
            chatRoomVo.setRoomName(contact.getContactName());
            chatRoomVo.setAvatar(contact.getContactIcon());
            QueryWrapper<AqUserChat> wrapper = new QueryWrapper<>();
            wrapper.eq("session_id", sessionId).eq("is_read", 0)
                    .eq("c_receive_user_id", uId);
            chatRoomVo.setUnreadCount(userChatService.count(wrapper));
            chatRoomVo.setIndex(n++);  // 待完善

            /*最后一条信息*/
            // getLastChatBySessionId可获取到最后一条消息的内容和时间
            LastMessageVo lastMessageVo = userChatService.getLastChatBySessionId(sessionId);
            lastMessageVo = lastMessageVo == null ? new LastMessageVo("") : lastMessageVo;
            lastMessageVo.setSenderId(contact.getContactId());
            lastMessageVo.setUsername(contact.getContactName());
            chatRoomVo.setLastMessage(lastMessageVo);

            // list里面两条数据，0为用户自己，1为联系人
            List<UserMessageVo> userMessageVoList = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                UserMessageVo userMessageVo = new UserMessageVo();
                UserStatusVo userStatusVo = new UserStatusVo();
                if (i == 0) {
                    // 用户自己信息
                    userMessageVo.set_id(userMessage.getUserId());
                    userMessageVo.setUsername(userMessage.getUserName());
                    userMessageVo.setAvatar(userMessage.getIcon());

                    userStatusVo.setState("online");
                    /*userStatusVo.setLastChanged("00:00:00");*/
                    userMessageVo.setStatus(userStatusVo);
                } else {
                    // 联系人信息
                    String cId = contact.getContactId();
                    userMessageVo.set_id(cId);
                    userMessageVo.setUsername(contact.getContactName());
                    userMessageVo.setAvatar(contact.getContactIcon());

                    /*if (onlineUsers.get(cId) != null) {
                        userStatusVo.setState("online");
                    } else {
                        userStatusVo.setState("offline");
                    }*/
                    userStatusVo.setState("offline");
                    /*userStatusVo.setLastChanged("00:00:00");*/
                    userMessageVo.setStatus(userStatusVo);
                }
                userMessageVoList.add(userMessageVo);
            }
            chatRoomVo.setUsers(userMessageVoList);

            chatRoomList.add(chatRoomVo);
        }

        return R.ok().data("listContact", chatRoomList);
    }
}

