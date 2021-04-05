package com.jz.aqjianzhi.ws_service.service.impl;

import com.jz.aqjianzhi.ws_service.entity.AqUserContact;
import com.jz.aqjianzhi.ws_service.entity.vo.ContactVo;
import com.jz.aqjianzhi.ws_service.mapper.AqUserContactMapper;
import com.jz.aqjianzhi.ws_service.service.AqUserContactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xyk
 * @since 2021-04-03
 */
@Service
public class AqUserContactServiceImpl extends ServiceImpl<AqUserContactMapper, AqUserContact> implements AqUserContactService {

    @Override
    public List<ContactVo> getAllContact(String uId) {
        return baseMapper.getAllContact(uId);
    }
}
