package com.jimuv.common.domain.video.build.effect;

import com.jimuv.common.util.FFmpegStrUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Accessors(chain = true)
@ApiModel(value = "旋转设置")
public class Rotate {

    private static final String ROTATE = "rotate";
    private static final String RADIAN = "*PI/180";
    private static final String RADIAN_TIME = "*t";
    private static final String WIDTH = "ow=hypot(iw,ih)";
    private static final String HEIGHT = "oh=ow";
    private static final String COLOR = "c=black@0";

    @ApiModelProperty(value = "旋转度")
    private BigDecimal rotation;

    @ApiModelProperty(value = "是否保持旋转")
    private Boolean isKeep = false;

    private String buildRotate() {
        return ROTATE +
                FFmpegStrUtils.EQUAL +
                FFmpegStrUtils.SINGLE +
                String.valueOf(rotation) +
                (isKeep ? RADIAN_TIME : FFmpegStrUtils.EMPTY) +
                RADIAN +
                FFmpegStrUtils.COLON +
                WIDTH +
                FFmpegStrUtils.COLON +
                HEIGHT +
                FFmpegStrUtils.COLON +
                COLOR +
                FFmpegStrUtils.SINGLE;
    }

    public String build() {
        if (Objects.nonNull(rotation) && rotation.compareTo(BigDecimal.ZERO) != 0) {
            return FFmpegStrUtils.COMMA +
                    buildRotate();
        }
        return FFmpegStrUtils.EMPTY;
    }

}
