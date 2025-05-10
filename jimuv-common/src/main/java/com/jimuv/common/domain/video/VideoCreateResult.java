package com.jimuv.common.domain.video;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "视频创建结果")
public class VideoCreateResult {

    @ApiModelProperty(value = "任务id")
    private String id;

    @ApiModelProperty(value = "任务结果")
    private Boolean result;

    @ApiModelProperty(value = "任务时间，单位：秒")
    private Double time;

    @ApiModelProperty(value = "视频地址")
    private String video;

    @ApiModelProperty(value = "视频缩略图地址")
    private String videoThumbnail;

    @ApiModelProperty(value = "视频音频地址")
    private String videoAudio;
}
