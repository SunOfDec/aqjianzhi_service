package com.jz.aqjianzhi.task_service.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class QueryCommentVo {

    private String cId;

    private String uId;

    private String uName;

    private String uIcon;

    private String cContent;

    private Date updateTime;
}
