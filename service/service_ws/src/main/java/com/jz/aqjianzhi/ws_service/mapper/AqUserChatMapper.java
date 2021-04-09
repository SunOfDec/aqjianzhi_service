package com.jz.aqjianzhi.ws_service.mapper;

import com.jz.aqjianzhi.ws_service.entity.AqUserChat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jz.aqjianzhi.ws_service.entity.vo.LastMessageVo;
import com.jz.aqjianzhi.ws_service.entity.vo.MessageVo;
import org.apache.ibatis.annotations.Param;

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

    LastMessageVo getLastChatBySessionId(@Param("sessionId") String sessionId);

    List<MessageVo> getMessages(@Param("sessionId") String sessionId);

    Date getFirstChatTime(@Param("sessionId") String sessionId);

    int saveChatMessage(@Param("cId") String cId,
                        @Param("sessionId") String sessionId,
                        @Param("content") String content,
                        @Param("senderId") String senderId,
                        @Param("receiverId") String receiverId,
                        @Param("cLevel") Long cLevel,
                        @Param("timestamp") Date timestamp);
}
