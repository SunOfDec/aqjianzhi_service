package com.jz.aqjianzhi.msm_service.controller;

import com.jz.aqjianzhi.msm_service.service.MsmService;
import com.jz.aqjianzhi.msm_service.utils.RandomUtil;
import com.jz.aqjianzhi.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/sendCode/{mail}")
    public R sendCode(@PathVariable String mail) {
        // 判断Redis中验证码是否为空
        String code = redisTemplate.opsForValue().get(mail);
        if (StringUtils.hasLength(code)) {
            return R.ok();
        }

        code = RandomUtil.getSixBitRandom();
        msmService.sendSimpleMail(
                "1774409118@qq.com",
                mail,
                "验证码",
                "阿Q兼职：您本次操作的验证码为" + code + "，有效时长5分钟"
        );

        // 将邮箱和验证码临时存储到Redis中, 并设置过期时间
        redisTemplate.opsForValue().set(mail, code, 5, TimeUnit.MINUTES);


        return R.ok();
    }

}
