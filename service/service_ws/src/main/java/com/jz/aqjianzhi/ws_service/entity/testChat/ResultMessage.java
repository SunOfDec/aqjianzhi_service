package com.jz.aqjianzhi.ws_service.entity.testChat;

import lombok.Data;

@Data
public class ResultMessage {

    private boolean isSystem;

    private String fromName;

    private Object message;
}
