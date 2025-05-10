package com.jimuv.common.domain.video.build;

import com.jimuv.common.util.FFmpegStrUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "视频拼接")
public class VideoBuild {

    @ApiModelProperty(value = "类型：text、audio、element")
    private String type = FFmpegStrUtils.TEXT;

    @ApiModelProperty(value = "文本内容")
    private String textContent;

    @ApiModelProperty(value = "音频地址")
    private String audioUrl;

    @ApiModelProperty(value = "元素地址")
    private String elementUrl;

    @ApiModelProperty(value = "效果")
    private Effect effect;

}
