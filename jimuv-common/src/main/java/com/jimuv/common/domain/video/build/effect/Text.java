package com.jimuv.common.domain.video.build.effect;

import com.jimuv.common.util.FFmpegStrUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Accessors(chain = true)
@ApiModel(value = "文字效果")
public class Text {

    private static final Integer FONTSIZE = 20;
    private static final String FONTCOLOR = "black";
    private static final BigDecimal FONTCOLORALPHA = BigDecimal.ONE;

    private static final Integer BOX = 0;
    private static final String BOXCOLOR = "green";
    private static final BigDecimal BOXCOLORALPHA = BigDecimal.ONE;

    private static final String BORDERWIDTH = "0";
    private static final String BORDERCOLOR = "red";
    private static final BigDecimal BOXBORDERWIDTH = BigDecimal.valueOf(10L);
    private static final BigDecimal BORDERCOLORALPHA = BigDecimal.ONE;

    private static final String DRAWTEXT_EQ = "drawtext=";
    private static final String TEXT_EQ = "text=";
    private static final String FONTFILE_EQ = "fontfile=";
    private static final String FONTSIZE_EQ = "fontsize=";
    private static final String FONTCOLOR_EQ = "fontcolor=";
    private static final String BOX_EQ = "box=";
    private static final String BOXCOLOR_EQ = "boxcolor=";
    private static final String BORDERW_EQ = "borderw=";
    private static final String BOXBORDERW_EQ = "boxborderw=";
    private static final String BORDERCOLOR_EQ = "bordercolor=";
    private static final String ALPHA_EQ = "@";
    private static final String X_EQ = "x=";
    private static final String Y_EQ = "y=";
    private static final String FIX_BOUNDS = "fix_bounds=1";

    @ApiModelProperty(value = "字体地址")
    private String fontFile;

    @ApiModelProperty(value = "文字大小")
    private Integer fontSize = FONTSIZE;

    @ApiModelProperty(value = "文字颜色")
    private String fontColor = FONTCOLOR;

    @ApiModelProperty(value = "文字颜色透明度")
    private BigDecimal fontColorAlpha = FONTCOLORALPHA;

    @ApiModelProperty(value = "是否背景：0=无背景、1=有背景")
    private Integer box = BOX;

    @ApiModelProperty(value = "背景颜色")
    private String boxColor = BOXCOLOR;

    @ApiModelProperty(value = "背景宽度")
    private BigDecimal boxBorderWidth = BOXBORDERWIDTH;

    @ApiModelProperty(value = "背景颜色透明度")
    private BigDecimal boxColorAlpha = BOXCOLORALPHA;

    @ApiModelProperty(value = "边框大小")
    private String borderWidth = BORDERWIDTH;

    @ApiModelProperty(value = "边框颜色")
    private String borderColor = BORDERCOLOR;

    @ApiModelProperty(value = "边框颜色透明度")
    private BigDecimal borderColorAlpha = BORDERCOLORALPHA;

    public String build(String overlay, String text) {
        return DRAWTEXT_EQ +
                TEXT_EQ + FFmpegStrUtils.SINGLE + text + FFmpegStrUtils.SINGLE + FFmpegStrUtils.COLON +
                FONTFILE_EQ + fontFile + FFmpegStrUtils.COLON +
                FONTSIZE_EQ + fontSize + FFmpegStrUtils.COLON +
                FONTCOLOR_EQ + fontColor + ALPHA_EQ + fontColorAlpha + FFmpegStrUtils.COLON +
                BOX_EQ + box + FFmpegStrUtils.COLON +
                BOXCOLOR_EQ + boxColor + ALPHA_EQ + boxColorAlpha + FFmpegStrUtils.COLON +
                BOXBORDERW_EQ + boxBorderWidth + FFmpegStrUtils.COLON +
                BORDERW_EQ + borderWidth + FFmpegStrUtils.COLON +
                BORDERCOLOR_EQ + borderColor + ALPHA_EQ + boxColorAlpha + FFmpegStrUtils.COLON +
                FIX_BOUNDS + FFmpegStrUtils.COLON +
                overlay;
    }

    public void initParam() {
        if (Objects.isNull(fontSize)) {
            fontSize = FONTSIZE;
        }
        if (StringUtils.isEmpty(fontColor)) {
            fontColor = FONTCOLOR;
        }
        if (Objects.isNull(fontColorAlpha)) {
            fontColorAlpha = FONTCOLORALPHA;
        }
        if (Objects.isNull(box)) {
            box = BOX;
        }
        if (StringUtils.isEmpty(boxColor)) {
            boxColor = BOXCOLOR;
        }
        if (Objects.isNull(boxColorAlpha)) {
            boxColorAlpha = BOXCOLORALPHA;
        }
        if (StringUtils.isEmpty(boxBorderWidth)) {
            boxBorderWidth = BOXBORDERWIDTH;
        }
        if (StringUtils.isEmpty(borderWidth)) {
            borderWidth = BORDERWIDTH;
        }
        if (StringUtils.isEmpty(borderColor)) {
            borderColor = BORDERCOLOR;
        }
        if (Objects.isNull(borderColorAlpha)) {
            borderColorAlpha = BORDERCOLORALPHA;
        }
    }

}
