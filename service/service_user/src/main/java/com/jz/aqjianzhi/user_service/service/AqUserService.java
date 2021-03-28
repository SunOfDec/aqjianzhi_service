package com.jz.aqjianzhi.user_service.service;

import com.jz.aqjianzhi.user_service.entity.AqUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jz.aqjianzhi.user_service.entity.vo.RegisterVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xyk
 * @since 2021-03-28
 */
public interface AqUserService extends IService<AqUser> {

    String login(AqUser user);

    void register(RegisterVo registerVo);
}
