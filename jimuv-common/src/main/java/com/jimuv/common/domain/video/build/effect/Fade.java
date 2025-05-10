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
@ApiModel(value = "淡入淡出")
public class Fade {

    private static final String FADE = "fade";
    private static final String IN = "in";
    private static final String OUT = "out";
    private static final String ST = "st";
    private static final String D = "d";

    @ApiModelProperty(value = "淡入时间")
    private BigDecimal fadeInTime;

    @ApiModelProperty(value = "淡出时间")
    private BigDecimal fadeOutTime;

    private String buildFadeIn(Time time) {
        return FADE +
                FFmpegStrUtils.EQUAL +
                IN +
                FFmpegStrUtils.COLON +
                ST +
                FFmpegStrUtils.EQUAL +
                time.getStartTime() +
                FFmpegStrUtils.COLON +
                D +
                FFmpegStrUtils.EQUAL +
                fadeInTime;
    }

    private String buildFadeOut(Time time) {
        return FADE +
                FFmpegStrUtils.EQUAL +
                OUT +
                FFmpegStrUtils.COLON +
                ST +
                FFmpegStrUtils.EQUAL +
                time.getEndTime().subtract(fadeOutTime) +
                FFmpegStrUtils.COLON +
                D +
                FFmpegStrUtils.EQUAL +
                fadeOutTime;
    }

    public String build(Time time) {
        StringBuilder build = new StringBuilder();
        if (Objects.nonNull(fadeInTime) && fadeInTime.compareTo(BigDecimal.ZERO) > 0 && Objects.nonNull(fadeOutTime) && fadeOutTime.compareTo(BigDecimal.ZERO) > 0) {
            build.append(FFmpegStrUtils.COMMA)
                    .append(buildFadeIn(time))
                    .append(FFmpegStrUtils.COMMA)
                    .append(buildFadeOut(time));
        } else {
            if (Objects.nonNull(fadeInTime) && fadeInTime.compareTo(BigDecimal.ZERO) > 0) {
                build.append(FFmpegStrUtils.COMMA)
                        .append(buildFadeIn(time));
            }
            if (Objects.nonNull(fadeOutTime) && fadeOutTime.compareTo(BigDecimal.ZERO) > 0) {
                build.append(FFmpegStrUtils.COMMA)
                        .append(buildFadeOut(time));
            }
        }
        return build.toString();
    }

}
