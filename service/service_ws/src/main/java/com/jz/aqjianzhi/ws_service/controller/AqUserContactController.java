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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        /*QueryWrapper<AqUserContact> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uId);
        List<AqUserContact> list = userContactService.list(wrapper);*/
        List<ContactVo> allContact = userContactService.getAllContact(uId);

        List<ChatRoomVo> chatRoomList = new ArrayList<>();
        int n = 0;
        for (ContactVo contact : allContact) {
            ChatRoomVo chatRoomVo = new ChatRoomVo();
            /*聊天室基本信息*/
            chatRoomVo.setRoomId(contact.getSessionId());
            chatRoomVo.setRoomName(contact.getUName());
            chatRoomVo.setAvatar(contact.getUIcon());
            chatRoomVo.setUnreadCount(0);  // 待完善
            chatRoomVo.setIndex(n++);  // 待完善

            /*最后一条信息*/
            LastMessageVo lastMessageVo = new LastMessageVo();
            lastMessageVo.setContent("test");
            lastMessageVo.setSenderId(contact.getCId());
            lastMessageVo.setUsername(contact.getUName());
            lastMessageVo.setTimestamp("00:00");
            chatRoomVo.setLastMessage(lastMessageVo);

            List<UserMessageVo> userMessageVoList = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                UserMessageVo userMessageVo = new UserMessageVo();
                userMessageVo.set_id(contact.getCId());
                if (i == 0) {
                    userMessageVo.setUsername("发送者");
                } else {
                    userMessageVo.setUsername(contact.getUName());
                }
                userMessageVo.setAvatar(contact.getUIcon());

                UserStatusVo userStatusVo = new UserStatusVo();
                userStatusVo.setState("online");
                userStatusVo.setLastChanged("00:00:00");
                userMessageVo.setStatus(userStatusVo);

                userMessageVoList.add(userMessageVo);
            }
            chatRoomVo.setUsers(userMessageVoList);

            chatRoomList.add(chatRoomVo);
        }

        return R.ok().data("listContact", chatRoomList);
    }

    /*private AqUserChat getLastestChatBySessionId(String sessionId) {
        QueryWrapper<AqUserChat> wrapper = new QueryWrapper<>();
        wrapper.eq("session_id", sessionId);
        List<AqUserChat> list = userChatService.list(wrapper);
    }*/
}

