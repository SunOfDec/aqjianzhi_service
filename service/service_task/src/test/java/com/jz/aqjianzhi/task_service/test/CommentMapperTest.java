package com.jz.aqjianzhi.task_service.test;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jz.aqjianzhi.task_service.entity.vo.QueryCommentVo;
import com.jz.aqjianzhi.task_service.mapper.AqTaskCommentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentMapperTest {

    @Resource
    private AqTaskCommentMapper commentMapper;

    @Test
    public void test01() {
        Page<QueryCommentVo> commentList = commentMapper.pageQueryCommentById(new Page<>(0, 10), "1378668361579290626");
        System.out.println("以下是评论数量>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<QueryCommentVo> records = commentList.getRecords();
        for (QueryCommentVo comment : records) {
            System.out.println(comment);
        }
    }
}
