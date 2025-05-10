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
@ApiModel(value = "透明设置")
public class Transparent {

    private static final String FORMAT = "format";
    private static final String RGBA = "rgba";
    private static final String COLOR = "colorchannelmixer";
    private static final String AA = "aa";

    @ApiModelProperty(value = "透明度")
    private BigDecimal transparency = BigDecimal.ONE;

    private String buildTransparent() {
        return FORMAT +
                FFmpegStrUtils.EQUAL +
                RGBA +
                FFmpegStrUtils.COMMA +
                COLOR +
                FFmpegStrUtils.EQUAL +
                AA +
                FFmpegStrUtils.EQUAL +
                String.valueOf(transparency);
    }

    public String build() {
        if (Objects.nonNull(transparency) && transparency.compareTo(BigDecimal.ZERO) >= 0 && transparency.compareTo(BigDecimal.ONE) <= 0) {
            return buildTransparent();
        }
        return FFmpegStrUtils.EMPTY;
    }

    public void initParam() {
        if (Objects.isNull(transparency)) {
            transparency = BigDecimal.ONE;
        }
    }
}
