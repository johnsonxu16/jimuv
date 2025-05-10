package com.jimuv.common.domain.video.build.effect.move;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@ApiModel(value = "移动入场")
public class In {

    @ApiModelProperty(value = "移动方向：1=左、2=右、3=上、4=下，默认左")
    private Integer moveDirection = 1;

    @ApiModelProperty(value = "移动持续时间，单位秒")
    private BigDecimal moveTime = BigDecimal.ZERO;

    @ApiModelProperty(value = "移动速度，单位px/每秒，默认每秒100px")
    private Integer moveSpeed = 100;
}
