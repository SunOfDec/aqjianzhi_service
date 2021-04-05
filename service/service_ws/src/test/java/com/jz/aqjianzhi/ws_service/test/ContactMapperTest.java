package com.jz.aqjianzhi.ws_service.test;

import com.jz.aqjianzhi.ws_service.entity.vo.ContactVo;
import com.jz.aqjianzhi.ws_service.mapper.AqUserContactMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactMapperTest {

    @Resource
    private AqUserContactMapper contactMapper;

    @Test
    public void test01() {
        List<ContactVo> allContact = contactMapper.getAllContact("1");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(allContact);
    }

    @Test
    public void testUUID() {
        String s = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        System.out.println(s);
    }
}
