package com.jz.aqjianzhi.task_service.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jz.aqjianzhi.task_service.entity.AqTask;
import com.jz.aqjianzhi.task_service.entity.vo.QueryTaskVo;
import com.jz.aqjianzhi.task_service.service.AqTaskService;
import com.jz.aqjianzhi.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xyk
 * @since 2021-04-03
 */
@RestController
@RequestMapping("/task")
@CrossOrigin
public class AqTaskController {

    @Autowired
    private AqTaskService taskService;

    @PutMapping("/addTask")
    public R addTask(@RequestBody AqTask task) {
        taskService.save(task);
        return R.ok();
    }


    /**
     * 多表查询所有任务
     * @return
     */
   /* @GetMapping("/queryAllTask")
    public R queryAllTask() {
        List<QueryTaskVo> taskList = taskService.queryAllTaskForMultiTable();
        return R.ok().data("taskList", taskList);
    }*/

    /**
     * 分页查询任务
     */
    @GetMapping("/pageTaskList/{current}/{size}")
    public R pageTaskList(@PathVariable Long current,
                          @PathVariable Long size) {

        // 1.创建page对象
        Page<QueryTaskVo> taskPage = taskService.pageQueryAllTaskForMultiTable(new Page<>(current, size));
        /*long total = taskPage.getTotal();
        List<QueryTaskVo> records = taskPage.getRecords();*/

        return R.ok().data("taskPage", taskPage);
    }

}

