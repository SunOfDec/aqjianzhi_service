package com.jz.aqjianzhi.user_service.mapper;

import com.jz.aqjianzhi.user_service.entity.AqUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jz.aqjianzhi.user_service.entity.vo.QueryUserInfoByTokenVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xyk
 * @since 2021-03-28
 */
public interface AqUserMapper extends BaseMapper<AqUser> {

    QueryUserInfoByTokenVo queryUserInfoByToken(@Param("uId") String uId);
}
