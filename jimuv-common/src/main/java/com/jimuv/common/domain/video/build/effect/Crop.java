package com.jimuv.common.domain.video.build.effect;

import com.jimuv.common.util.FFmpegStrUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(chain = true)
@ApiModel(value = "元素裁剪")
public class Crop {

    private static final String CROP = "crop";

    @ApiModelProperty(value = "裁剪，从x坐标开始，默认从0开始")
    private Integer cropX = 0;

    @ApiModelProperty(value = "裁剪，从y坐标开始，默认从0开始")
    private Integer cropY = 0;

    @ApiModelProperty(value = "裁剪宽度，裁剪宽度不能大于文件实际宽度")
    private Integer cropWidth;

    @ApiModelProperty(value = "裁剪高度，裁剪高度不能大于文件实际高度")
    private Integer cropHeight;

    private String buildCrop() {
        return CROP +
                FFmpegStrUtils.EQUAL +
                cropWidth +
                FFmpegStrUtils.COLON +
                cropHeight +
                FFmpegStrUtils.COLON +
                cropX +
                FFmpegStrUtils.COLON +
                cropY;
    }

    public String build() {
        if (Objects.isNull(cropWidth) || Objects.isNull(cropHeight) || cropWidth == 0 || cropHeight == 0) {
            return FFmpegStrUtils.EMPTY;
        }
        return FFmpegStrUtils.COMMA +
                buildCrop();
    }

}
