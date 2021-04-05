package com.jz.aqjianzhi.task_service.entity;

import java.math.BigDecimal;

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
@ApiModel(value="AqTask对象", description="")
public class AqTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "t_id", type = IdType.ID_WORKER_STR)
    private String tId;

    private String tName;

    private String userPublishId;

    private String userReceiveId;

    private String tDetail;

    private Integer categoryId;

    private Integer passagewayId;

    private Integer transactionId;

    private BigDecimal tPrice;

    @TableField(fill = FieldFill.INSERT)  // 自动填充
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)  // 自动填充
    private Date updateTime;

    private Integer stateId;

    private Boolean isFinish;

    private Date finishTime;

    private Integer tSeeNum;

    private Integer tLikeNum;

    private Integer tCommentNum;

    @TableLogic  // 逻辑删除注解
    private Boolean isDelete;


}
