package com.jz.aqjianzhi.task_service.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jz.aqjianzhi.task_service.entity.AqTaskComment;
import com.jz.aqjianzhi.task_service.entity.vo.QueryCommentVo;
import com.jz.aqjianzhi.task_service.mapper.AqTaskCommentMapper;
import com.jz.aqjianzhi.task_service.service.AqTaskCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xyk
 * @since 2021-04-04
 */
@Service
public class AqTaskCommentServiceImpl extends ServiceImpl<AqTaskCommentMapper, AqTaskComment> implements AqTaskCommentService {

    @Override
    public Page<QueryCommentVo> pageQueryCommentById(Page<QueryCommentVo> page, String tId) {
        return baseMapper.pageQueryCommentById(page, tId);
    }
}
