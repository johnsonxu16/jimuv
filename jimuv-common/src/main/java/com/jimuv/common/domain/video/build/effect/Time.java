package com.jimuv.common.domain.video.build.effect;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Accessors(chain = true)
@ApiModel(value = "时间设置")
public class Time {

    @ApiModelProperty(value = "开始时间，单位秒")
    private BigDecimal startTime = BigDecimal.ZERO;

    @ApiModelProperty(value = "结束时间，单位秒")
    private BigDecimal endTime;

    public void initParam(BigDecimal duration) {
        if (Objects.isNull(startTime)) {
            startTime = BigDecimal.ZERO;
        }
        if (Objects.isNull(endTime) || endTime.compareTo(BigDecimal.ZERO) == 0) {
            endTime = duration;
        }
    }

    public String buildTime(BigDecimal duration) {
        if (Objects.isNull(startTime)) {
            startTime = BigDecimal.ZERO;
        }
        if (Objects.isNull(endTime) || endTime.compareTo(BigDecimal.ZERO) == 0) {
            endTime = duration;
        }
        return ":enable='between(t," + startTime + "," + endTime + ")'";
    }

    public void fadeTime(Fade fade) {
        if (Objects.nonNull(fade)) {
            if (Objects.nonNull(fade.getFadeInTime()) && fade.getFadeInTime().compareTo(BigDecimal.ZERO) > 0) {
                startTime = startTime.add(new BigDecimal("0.05"));
            }
            if (Objects.nonNull(fade.getFadeOutTime()) && fade.getFadeOutTime().compareTo(BigDecimal.ZERO) > 0) {
                endTime = endTime.subtract(new BigDecimal("0.05"));
            }
        }
    }
}