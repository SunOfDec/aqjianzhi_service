package com.jz.aqjianzhi.task_service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jz.aqjianzhi.task_service.entity.AqTaskComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jz.aqjianzhi.task_service.entity.vo.QueryCommentVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xyk
 * @since 2021-04-04
 */
public interface AqTaskCommentService extends IService<AqTaskComment> {

    Page<QueryCommentVo> pageQueryCommentById(Page<QueryCommentVo> page, String tId);
}
