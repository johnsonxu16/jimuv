package com.jimuv.common.domain.video.init;

import com.jimuv.common.util.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Accessors(chain = true)
@ApiModel(value = "视频初始化")
public class VideoInit {

    private static final String NAME = "jimuv";
    private static final Integer WIDTH = 1080;
    private static final Integer HEIGHT = 1920;
    private static final BigDecimal DURATION = BigDecimal.valueOf(5L);
    private static final String BACKGROUNDCOLOR = "white";
    private static final Integer FRAMERATE = 25;
    private static final Integer CRF = 23;
    private static final BigDecimal THUMBNAILTIME = BigDecimal.ZERO;

    @ApiModelProperty(value = "任务id")
    private String id = DateUtils.getTimeStamp();

    @ApiModelProperty(value = "任务名称")
    private String name = NAME;

    @ApiModelProperty(value = "宽")
    private Integer width = WIDTH;

    @ApiModelProperty(value = "高")
    private Integer height = HEIGHT;

    @ApiModelProperty(value = "时长")
    private BigDecimal duration = DURATION;

    @ApiModelProperty(value = "背景颜色")
    private String backgroundColor = BACKGROUNDCOLOR;

    @ApiModelProperty(value = "背景图片")
    private String backgroundImage;

    @ApiModelProperty(value = "帧率：默认25")
    private Integer frameRate = FRAMERATE;

    @ApiModelProperty(value = "质量：默认23")
    private Integer crf = CRF;

    @ApiModelProperty(value = "缩略图截图时间，默认0秒第一帧")
    private BigDecimal thumbnailTime = THUMBNAILTIME;

    @ApiModelProperty(value = "debug模式")
    private Boolean debug = false;

    public void initParam() {
        if (StringUtils.isEmpty(id)) {
            id = DateUtils.getTimeStamp();
        }
        if (StringUtils.isEmpty(name)) {
            name = NAME;
        }
        if (Objects.isNull(width) || width == 0) {
            width = WIDTH;
        }
        if (Objects.isNull(height) || height == 0) {
            height = HEIGHT;
        }
        if (Objects.isNull(duration) || duration.compareTo(BigDecimal.ZERO) == 0) {
            duration = DURATION;
        }
        if (StringUtils.isEmpty(backgroundColor)) {
            backgroundColor = BACKGROUNDCOLOR;
        }
        if (Objects.isNull(frameRate) || frameRate == 0) {
            frameRate = FRAMERATE;
        }
        if (Objects.isNull(crf) || crf == 0) {
            crf = CRF;
        }
        if (Objects.isNull(thumbnailTime) || thumbnailTime.compareTo(BigDecimal.ZERO) == 0) {
            thumbnailTime = THUMBNAILTIME;
        }
        if (Objects.isNull(debug)) {
            debug = false;
        }
    }

}
