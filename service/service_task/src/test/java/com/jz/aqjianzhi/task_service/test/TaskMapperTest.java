package com.jz.aqjianzhi.task_service.test;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jz.aqjianzhi.task_service.entity.AqTask;
import com.jz.aqjianzhi.task_service.entity.vo.QueryTaskVo;
import com.jz.aqjianzhi.task_service.mapper.AqTaskMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTest {

    @Resource
    private AqTaskMapper taskMapper;

    @Test
    public void testMapper1() {
        Page<QueryTaskVo> pageTaskList = taskMapper.pageQueryAllTaskForMultiTable(new Page<>(0, 5));
        System.out.println(pageTaskList);
    }
}
