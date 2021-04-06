package com.jz.aqjianzhi.ws_service.service;

import com.jz.aqjianzhi.ws_service.entity.AqUserChat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jz.aqjianzhi.ws_service.entity.vo.LastMessageVo;
import com.jz.aqjianzhi.ws_service.entity.vo.UserVo;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xyk
 * @since 2021-04-03
 */
public interface AqUserChatService extends IService<AqUserChat> {

    LastMessageVo getLastChatBySessionId(String sessionId);

    Date getFirstChatTime(String sessionId);
}
