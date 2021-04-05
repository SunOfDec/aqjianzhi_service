package com.jz.aqjianzhi.ws_service.mapper;

import com.jz.aqjianzhi.ws_service.entity.AqUserChat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
    List<AqUserChat> getLastChatBySessionId(String sessionId);
}
