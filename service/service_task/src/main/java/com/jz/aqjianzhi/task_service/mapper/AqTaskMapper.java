package com.jz.aqjianzhi.task_service.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jz.aqjianzhi.task_service.entity.AqTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jz.aqjianzhi.task_service.entity.vo.QueryTaskVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xyk
 * @since 2021-04-03
 */
public interface AqTaskMapper extends BaseMapper<AqTask> {

//    List<QueryTaskVo> pageQueryAllTaskForMultiTable(Page<QueryTaskVo> page);
    Page<QueryTaskVo> pageQueryAllTaskForMultiTable(Page<QueryTaskVo> page);
}
