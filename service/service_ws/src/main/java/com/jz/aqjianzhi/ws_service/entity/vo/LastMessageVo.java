package com.jz.aqjianzhi.ws_service.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LastMessageVo {

    private String content;

    private String senderId;

    private String username;

    private String timestamp;

    private boolean saved;

    private boolean distributed;

    private boolean seen;

    private boolean isNew;

    public LastMessageVo(String content) {
        this.content = content;
    }
}
