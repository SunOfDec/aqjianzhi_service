package com.jz.aqjianzhi.ws_service.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChatRoomVo {

    private String roomId;

    private String roomName;

    private String avatar;

    private Integer unreadCount;

    private Integer index;

    private LastMessageVo lastMessage;

    private List<UserMessageVo> users;
}
