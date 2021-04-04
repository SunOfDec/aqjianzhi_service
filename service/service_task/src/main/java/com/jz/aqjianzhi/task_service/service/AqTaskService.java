package com.jz.aqjianzhi.task_service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jz.aqjianzhi.task_service.entity.AqTask;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jz.aqjianzhi.task_service.entity.vo.QueryTaskVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xyk
 * @since 2021-04-03
 */
public interface AqTaskService extends IService<AqTask> {

    Page<QueryTaskVo> pageQueryAllTaskForMultiTable(Page<QueryTaskVo> page);
}
