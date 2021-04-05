package com.jz.aqjianzhi.test;

import com.jz.aqjianzhi.user_service.UserApplication;
import com.jz.aqjianzhi.user_service.entity.vo.QueryUserInfoByTokenVo;
import com.jz.aqjianzhi.user_service.mapper.AqUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class UserMapperTest {

    @Autowired
    private AqUserMapper userMapper;

    @Test
    public void testMapper1() {
        QueryUserInfoByTokenVo userInfo = userMapper.queryUserInfoByToken("1");
        System.out.println(userInfo);
    }
}
