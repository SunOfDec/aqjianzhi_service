package com.jz.aqjianzhi.ws_service.mapper;

import com.jz.aqjianzhi.ws_service.entity.AqUserContact;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jz.aqjianzhi.ws_service.entity.vo.ContactVo;
import com.jz.aqjianzhi.ws_service.entity.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xyk
 * @since 2021-04-03
 */
public interface AqUserContactMapper extends BaseMapper<AqUserContact> {


    List<ContactVo> getAllContact(@Param("uId") String uId);

    UserVo getUserMessage(@Param("uId") String uId);


}
