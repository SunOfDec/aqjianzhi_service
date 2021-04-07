package com.jz.aqjianzhi.ws_service.service.impl;

import com.jz.aqjianzhi.ws_service.entity.AqUserChat;
import com.jz.aqjianzhi.ws_service.entity.vo.LastMessageVo;
import com.jz.aqjianzhi.ws_service.mapper.AqUserChatMapper;
import com.jz.aqjianzhi.ws_service.service.AqUserChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xyk
 * @since 2021-04-03
 */
@Service
public class AqUserChatServiceImpl extends ServiceImpl<AqUserChatMapper, AqUserChat> implements AqUserChatService {

    @Override
    public LastMessageVo getLastChatBySessionId(String sessionId) {
        return baseMapper.getLastChatBySessionId(sessionId);
    }

    @Override
    public Date getFirstChatTime(String sessionId) {
        return baseMapper.getFirstChatTime(sessionId);
    }

    @Override
    public int saveChatMessage(String cId, String sessionId, String content, String senderId, String receiverId, Long cLevel, Date timestamp) {
        return baseMapper.saveChatMessage(cId, sessionId, content, senderId, receiverId, cLevel, timestamp);
    }
}
