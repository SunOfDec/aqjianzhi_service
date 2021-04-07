package com.jz.aqjianzhi.ws_service.mapper;

import com.jz.aqjianzhi.ws_service.entity.AqUserChat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jz.aqjianzhi.ws_service.entity.vo.LastMessageVo;
import com.jz.aqjianzhi.ws_service.entity.vo.MessageVo;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xyk
 * @since 2021-04-03
 */
public interface AqUserChatMapper extends BaseMapper<AqUserChat> {

    LastMessageVo getLastChatBySessionId(String sessionId);

    List<MessageVo> getMessages(String sessionId);

    Date getFirstChatTime(String sessionId);

    int saveChatMessage(String cId, String sessionId, String content, String senderId, String receiverId, Long cLevel, Date timestamp);
}
