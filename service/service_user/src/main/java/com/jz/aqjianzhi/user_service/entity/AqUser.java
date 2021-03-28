package com.jz.aqjianzhi.user_service.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xyk
 * @since 2021-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AqUser对象", description="")
public class AqUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "u_id", type = IdType.AUTO)
    private Long uId;

    private String uMobile;

    private String uName;

    private String uPassword;

    private String uIcon;

    @TableField(fill = FieldFill.INSERT)  // 自动填充
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)  // 自动填充
    private Date updateTime;

    private Boolean isActive;

    @TableLogic  // 逻辑删除注解
    private Boolean isDelete;

    private String uRealName;

    private String uGender;

    private Integer uAge;

    private String uEmail;

    private Integer uStatus;

    private String uAddress;

    private String uDetail;

    private String uIdNumber;

    private String uStuNumber;

    private Long uKMount;

    private Integer uCredit;


}
