package com.jz.aqjianzhi.task_service.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jz.aqjianzhi.task_service.entity.AqTaskComment;
import com.jz.aqjianzhi.task_service.entity.vo.QueryCommentVo;
import com.jz.aqjianzhi.task_service.service.AqTaskCommentService;
import com.jz.aqjianzhi.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xyk
 * @since 2021-04-04
 */
@RestController
@RequestMapping("/task/comment")
@CrossOrigin
public class AqTaskCommentController {

    @Autowired
    private AqTaskCommentService taskCommentService;

    @PostMapping("/addComment")
    public R addComment(@RequestBody AqTaskComment taskComment) {
        boolean save = taskCommentService.save(taskComment);
        return save ? R.ok() : R.error();
    }

    @GetMapping("/pageQueryComment/{current}/{size}/{tId}")
    public R pageQueryComment(@PathVariable Long current,
                              @PathVariable Long size,
                              @PathVariable String tId) {

        Page<QueryCommentVo> commentList = taskCommentService.pageQueryCommentById(new Page<>(current, size), tId);

        return R.ok().data("commentList", commentList);
    }
}

