package com.jz.aqjianzhi.task_service.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class QueryTaskVo {

    private String tId;

    private String tName;

    private String tDetail;

    private BigDecimal tPrice;

    private String category;

    private String passageway;

    private String transaction;

    private String state;

    private Boolean isFinish;

    private Date finishTime;

    private Integer tSeeNum;

    private Integer tLikeNum;

    private Integer tCommentNum;

    private Date createTime;

    private Date updateTime;

    private String uName;

    private String uIcon;

    private String userPublishId;
}
