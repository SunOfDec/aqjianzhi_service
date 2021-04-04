package com.jz.aqjianzhi.user_service.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class QueryUserInfoByTokenVo {

    private Long uId;

    private String uMobile;

    private String uName;

    private String uIcon;

    private Date createTime;

    private Date updateTime;

    private String uRealName;

    private String uGender;

    private Integer uAge;

    private String uEmail;

    private String status;

    private String uAddress;

    private String uDetail;

    private String uIdNumber;

    private String uStuNumber;

    private Long uKMount;

    private Integer uCredit;
}
