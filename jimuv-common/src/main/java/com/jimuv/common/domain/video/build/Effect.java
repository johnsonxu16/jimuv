package com.jimuv.common.domain.video.build;

import com.jimuv.common.domain.video.build.effect.Audio;
import com.jimuv.common.domain.video.build.effect.Crop;
import com.jimuv.common.domain.video.build.effect.Fade;
import com.jimuv.common.domain.video.build.effect.Flip;
import com.jimuv.common.domain.video.build.effect.Gblur;
import com.jimuv.common.domain.video.build.effect.Move;
import com.jimuv.common.domain.video.build.effect.Overlay;
import com.jimuv.common.domain.video.build.effect.Radius;
import com.jimuv.common.domain.video.build.effect.Rotate;
import com.jimuv.common.domain.video.build.effect.Scale;
import com.jimuv.common.domain.video.build.effect.Text;
import com.jimuv.common.domain.video.build.effect.Time;
import com.jimuv.common.domain.video.build.effect.Transparent;
import com.jimuv.common.domain.video.init.VideoInit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(chain = true)
@ApiModel(value = "效果调整")
public class Effect {

    @ApiModelProperty(value = "时间片段")
    private Time time;

    @ApiModelProperty(value = "位置叠加")
    private Overlay overlay;

    @ApiModelProperty(value = "文本设置")
    private Text text;

    @ApiModelProperty(value = "音频设置")
    private Audio audio;

    @ApiModelProperty(value = "裁剪设置")
    private Crop crop;

    @ApiModelProperty(value = "缩放设置")
    private Scale scale;

    @ApiModelProperty(value = "旋转设置")
    private Rotate rotate;

    @ApiModelProperty(value = "透明设置")
    private Transparent transparent;

    @ApiModelProperty(value = "高斯模糊")
    private Gblur gblur;

    @ApiModelProperty(value = "镜像翻转")
    private Flip flip;

    @ApiModelProperty(value = "圆角设置")
    private Radius radius;

    @ApiModelProperty(value = "淡入淡出")
    private Fade fade;

    @ApiModelProperty(value = "移动特效")
    private Move move;

    public void initParam(VideoInit videoInit) {
        if (Objects.isNull(transparent)) {
            transparent = new Transparent();
        }
        transparent.initParam();

        if (Objects.isNull(time)) {
            time = new Time();
        }
        time.initParam(videoInit.getDuration());

        if (Objects.isNull(overlay)) {
            overlay = new Overlay();
        }
        overlay.initParam();

        if (Objects.isNull(text)) {
            text = new Text();
        }
        text.initParam();

        if (Objects.isNull(audio)) {
            audio = new Audio();
        }
        audio.initParam();
    }

}
