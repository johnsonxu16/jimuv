package com.jimuv.common.domain.video.build.effect.move;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@ApiModel(value = "移动出场")
public class Out {

    @ApiModelProperty(value = "移动方向：1=左、2=右、3=上、4=下")
    private Integer moveDirection;

    @ApiModelProperty(value = "移动持续时间，单位秒")
    private BigDecimal moveTime;

    @ApiModelProperty(value = "移动速度，单位px/每秒")
    private Integer moveSpeed;
}
