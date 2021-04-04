package com.jz.aqjianzhi.task_service.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jz.aqjianzhi.task_service.entity.AqTask;
import com.jz.aqjianzhi.task_service.entity.vo.QueryTaskVo;
import com.jz.aqjianzhi.task_service.mapper.AqTaskMapper;
import com.jz.aqjianzhi.task_service.service.AqTaskService;
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
public class AqTaskServiceImpl extends ServiceImpl<AqTaskMapper, AqTask> implements AqTaskService {

    @Override
    public Page<QueryTaskVo> pageQueryAllTaskForMultiTable(Page<QueryTaskVo> page) {

        return baseMapper.pageQueryAllTaskForMultiTable(page);
    }
}
