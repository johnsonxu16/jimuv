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
@ApiModel(value = "音频效果")
public class Audio {

    @ApiModelProperty(value = "音量")
    private BigDecimal volume = BigDecimal.ONE;

    @ApiModelProperty(value = "音量渐入")
    private BigDecimal volumeFadeIn = BigDecimal.ZERO;

    @ApiModelProperty(value = "音量渐出")
    private BigDecimal volumeFadeOut = BigDecimal.ZERO;

    public void initParam() {
        if (Objects.isNull(volume)) {
            volume = BigDecimal.ONE;
        }
        if (Objects.isNull(volumeFadeIn)) {
            volumeFadeIn = BigDecimal.ONE;
        }
        if (Objects.isNull(volumeFadeOut)) {
            volumeFadeOut = BigDecimal.ONE;
        }
    }

    public String buildVolume() {
        return "volume=" + volume;
    }

    public String build(Time time) {
        return "atrim=start=0:end=" + time.getEndTime().subtract(time.getStartTime())
                + ",adelay=" + time.getStartTime().multiply(new BigDecimal(1000)) + "|0"
                + ",volume=" + volume
                + ((Objects.nonNull(volumeFadeIn) && volumeFadeIn.compareTo(BigDecimal.ZERO) > 0) ? ",afade=t=in:st=" + time.getStartTime() + ":d=" + time.getStartTime().add(volumeFadeIn) : FFmpegStrUtils.EMPTY)
                + ((Objects.nonNull(volumeFadeOut) && volumeFadeOut.compareTo(BigDecimal.ZERO) > 0) ? ",afade=t=out:st=" + time.getEndTime().subtract(volumeFadeOut) + ":d=" + volumeFadeOut : FFmpegStrUtils.EMPTY);
    }
}
