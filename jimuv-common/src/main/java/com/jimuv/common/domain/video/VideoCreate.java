package com.jimuv.common.domain.video;

import com.jimuv.common.domain.video.build.VideoBuild;
import com.jimuv.common.domain.video.init.VideoInit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "视频创建")
public class VideoCreate {

    @ApiModelProperty(value = "视频初始化")
    private VideoInit videoInit;

    @ApiModelProperty(value = "视频制作")
    private List<VideoBuild> videoBuild;
}
