package com.jz.aqjianzhi.ws_service.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class MessageVo {

    private String _id;

    private String content;

    private String senderId;

    private String receiverId;

    private String username;

    private String avatar;

    private Date date;

    private Date timestamp;

    private boolean system;

    private boolean saved;

    private boolean distributed;

    private boolean seen;

    private boolean disableActions;

    private boolean disableReactions;

    private String sessionId;

    private Long cLevel;
}
