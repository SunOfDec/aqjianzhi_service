package com.jz.aqjianzhi.user_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jz.aqjianzhi.service_base.exception.AqException;
import com.jz.aqjianzhi.user_service.entity.AqUser;
import com.jz.aqjianzhi.user_service.entity.vo.LoginVo;
import com.jz.aqjianzhi.user_service.entity.vo.QueryUserInfoByTokenVo;
import com.jz.aqjianzhi.user_service.entity.vo.RegisterVo;
import com.jz.aqjianzhi.user_service.mapper.AqUserMapper;
import com.jz.aqjianzhi.user_service.service.AqUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jz.aqjianzhi.utils.JwtUtils;
import com.jz.aqjianzhi.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xyk
 * @since 2021-03-28
 */
@Service
public class AqUserServiceImpl extends ServiceImpl<AqUserMapper, AqUser> implements AqUserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 用户登录
     * @param userVo
     * @return token值，token携带用户id与用户账号
     */
    @Override
    public String login(LoginVo userVo) {
        String account = userVo.getAccount();
        String password = userVo.getPassword();
        if (!StringUtils.hasLength(account) || !StringUtils.hasLength(password)) {
            throw  new AqException(20001, "登录失败！请检查账号或密码是否正确！");
        }
        QueryWrapper<AqUser> wrapper = new QueryWrapper<>();
        wrapper.eq("u_mobile", account);
        AqUser baseUser = baseMapper.selectOne(wrapper);
        if (baseUser == null) {
            throw  new AqException(20001, "登录失败！请检查账号或密码是否正确！");
        }
        if (!MD5.encrypt(password).equals(baseUser.getUPassword())) {
            throw  new AqException(20001, "登录失败！请检查账号或密码是否正确！");
        }
        if (!baseUser.getIsActive()) {
            throw  new AqException(20001, "抱歉！您的账户被禁用！");
        }

        return JwtUtils.getJwtToken(String.valueOf(baseUser.getUId()), baseUser.getUMobile());
    }

    /**
     * 注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String account = registerVo.getAccount();
        String password = registerVo.getPassword();

        if (!StringUtils.hasLength(account) || !StringUtils.hasLength(password) || !StringUtils.hasLength(code)) {
            throw  new AqException(20001, "注册失败，手机号、密码、验证码不能为空！");
        }

        // 获取Redis中的验证码
        String redisCode = redisTemplate.opsForValue().get(account);
        if (!code.equals(redisCode)) {
            throw  new AqException(20001, "注册失败");
        }

        // 判断注册账号是否已经存在
        QueryWrapper<AqUser> wrapper = new QueryWrapper<>();
        wrapper.eq("u_mobile", account);
        Integer integer = baseMapper.selectCount(wrapper);
        if (integer > 0) {
            throw  new AqException(20001, "注册失败");
        }

        // 将数据添加到数据库中
        AqUser user = new AqUser();
        user.setUMobile(account);
        user.setUPassword(MD5.encrypt(password));
        user.setIsActive(true);
        baseMapper.insert(user);
    }

    @Override
    public QueryUserInfoByTokenVo queryUserInfoByToken(String uId) {
        return baseMapper.queryUserInfoByToken(uId);
    }
}
