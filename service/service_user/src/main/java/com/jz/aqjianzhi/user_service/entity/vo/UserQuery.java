package com.jz.aqjianzhi.user_service.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserQuery {

    private String uName;

    private int gender;

    @ApiModelProperty(value = "查询开始时间", example = "2021-3-2 21:01:01")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2021-3-2 21:01:01")
    private String end;
}
