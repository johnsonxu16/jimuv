package com.jimuv.common.domain.video.build.effect;

import com.jimuv.common.util.FFmpegStrUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(chain = true)
@ApiModel(value = "高斯模糊")
public class Gblur {

    private static final String GBLUR = "gblur";
    private static final String SIGMA = "sigma";

    @ApiModelProperty(value = "模糊度")
    private Integer sigma = 0;

    private String buildGblur() {
        return GBLUR +
                FFmpegStrUtils.EQUAL +
                SIGMA +
                FFmpegStrUtils.EQUAL +
                sigma;
    }

    public String build() {
        if (Objects.isNull(sigma) || sigma == 0) {
            return FFmpegStrUtils.EMPTY;
        }
        return FFmpegStrUtils.COMMA +
                buildGblur();
    }

}
