package com.jimuv.common.domain.video.build.effect;

import com.jimuv.common.util.FFmpegStrUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(chain = true)
@ApiModel(value = "镜像翻转")
public class Flip {

    private static final String HFLIP = "hflip";
    private static final String VFLIP = "vflip";

    @ApiModelProperty(value = "是否水平翻转")
    private Boolean horizontalFlip = false;

    @ApiModelProperty(value = "是否垂直翻转")
    private Boolean verticalFlip = false;

    public String build() {
        StringBuilder build = new StringBuilder();
        if (Objects.nonNull(horizontalFlip) && horizontalFlip) {
            build.append(FFmpegStrUtils.COMMA)
                    .append(HFLIP);
        }
        if (Objects.nonNull(verticalFlip) && verticalFlip) {
            build.append(FFmpegStrUtils.COMMA)
                    .append(VFLIP);
        }
        return build.toString();
    }

}
