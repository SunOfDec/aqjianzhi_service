package com.jz.aqjianzhi.ws_service.entity;

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
 * @since 2021-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AqUserContact对象", description="")
public class AqUserContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "c_id", type = IdType.ID_WORKER)
    private Long cId;

    private Long userId;

    private Long userContactId;

    @TableField(fill = FieldFill.INSERT)  // 自动填充
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)  // 自动填充
    private Date updateTime;

    @TableLogic
    private Boolean isDeleted;


}
