package com.jz.aqjianzhi.ws_service.entity.vo;

import lombok.Data;

@Data
public class UserMessageVo {

    private String _id;

    private String username;

    private String avatar;

    private UserStatusVo status;
}
