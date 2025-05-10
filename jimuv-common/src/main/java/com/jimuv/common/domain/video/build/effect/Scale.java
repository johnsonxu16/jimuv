package com.jimuv.common.domain.video.build.effect;

import com.jimuv.common.util.FFmpegStrUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(chain = true)
@ApiModel(value = "元素缩放")
public class Scale {

    private static final String SCALE = "scale";

    @ApiModelProperty(value = "缩放宽度")
    private Integer scaleWidth;

    @ApiModelProperty(value = "缩放高度")
    private Integer scaleHeight;

    private String buildScale() {
        return SCALE +
                FFmpegStrUtils.EQUAL +
                scaleWidth +
                FFmpegStrUtils.COLON +
                scaleHeight;
    }

    public String build() {
        if (Objects.isNull(scaleWidth) || Objects.isNull(scaleHeight) || scaleWidth == 0 || scaleHeight == 0) {
            return FFmpegStrUtils.EMPTY;
        }
        return FFmpegStrUtils.COMMA +
                buildScale();
    }
}
