package com.jz.aqjianzhi.ws_service.service;

import com.jz.aqjianzhi.ws_service.entity.AqUserContact;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jz.aqjianzhi.ws_service.entity.vo.ContactVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xyk
 * @since 2021-04-03
 */
public interface AqUserContactService extends IService<AqUserContact> {

    List<ContactVo> getAllContact(String uId);
}
