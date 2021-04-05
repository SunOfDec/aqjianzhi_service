package com.jz.aqjianzhi.task_service.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jz.aqjianzhi.task_service.entity.AqTaskComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jz.aqjianzhi.task_service.entity.vo.QueryCommentVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xyk
 * @since 2021-04-04
 */
public interface AqTaskCommentMapper extends BaseMapper<AqTaskComment> {

    Page<QueryCommentVo> pageQueryCommentById(Page<QueryCommentVo> page, String tId);

}
