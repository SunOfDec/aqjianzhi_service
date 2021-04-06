package com.jz.aqjianzhi.ws_service.test;

import com.jz.aqjianzhi.ws_service.entity.AqUserChat;
import com.jz.aqjianzhi.ws_service.entity.vo.LastMessageVo;
import com.jz.aqjianzhi.ws_service.entity.vo.MessageVo;
import com.jz.aqjianzhi.ws_service.mapper.AqUserChatMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatMapperTest {

    @Resource
    private AqUserChatMapper chatMapper;

    @Test
    public void testMapper() {
        LastMessageVo lastMessage = chatMapper.getLastChatBySessionId("1bbcdc49f7d3414887d93a292d04f29d");
        System.out.println(lastMessage);
    }

    @Test
    public void testMapper02() {
        LastMessageVo lastMessage = chatMapper.getLastChatBySessionId("38b0056816d54878a02c4fd4f363a9a1");
        System.out.println(lastMessage);
    }

    @Test
    public void testMessageVo() {
        List<MessageVo> messages = chatMapper.getMessages("1bbcdc49f7d3414887d93a292d04f29d");
        System.out.println(messages);
    }

    @Test
    public void testGetFirstTime() {
        Date firstChatTime = chatMapper.getFirstChatTime("1bbcdc49f7d3414887d93a292d04f29d");
        System.out.println(firstChatTime);
    }
}
