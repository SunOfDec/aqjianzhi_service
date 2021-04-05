package com.jz.aqjianzhi.ws_service.test;

import com.jz.aqjianzhi.ws_service.entity.AqUserChat;
import com.jz.aqjianzhi.ws_service.mapper.AqUserChatMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatMapperTest {

    @Resource
    private AqUserChatMapper chatMapper;

    @Test
    public void testMapper() {
        List<AqUserChat> list = chatMapper.getLastChatBySessionId("1bbcdc49f7d3414887d93a292d04f29d");
        System.out.println(list);
        AqUserChat aqUserChat = list.get(0);
        System.out.println(aqUserChat);
    }
}
