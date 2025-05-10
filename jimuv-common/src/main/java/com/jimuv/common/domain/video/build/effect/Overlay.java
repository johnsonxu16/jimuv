package com.jimuv.common.domain.video.build.effect;

import com.jimuv.common.util.FFmpegStrUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(chain = true)
@ApiModel(value = "位置叠加")
public class Overlay {

    private static final String OVERLAY = "overlay";
    private static final String X_EQ = "x=";
    private static final String Y_EQ = "y=";
    private static final String X_W = "(W-w)";
    private static final String Y_H = "(H-h)";
    private static final String X_CENTER = "(W-w)/2";
    private static final String Y_CENTER = "(H-h)/2";
    private static final String X_W_TEXT = "(w-text_w)";
    private static final String Y_H_TEXT = "(h-text_h)";
    private static final String X_CENTER_TEXT = "(w-text_w)/2";
    private static final String Y_CENTER_TEXT = "(h-text_h)/2";
    private static final Integer ZERO = 0;

    @ApiModelProperty(value = "位置：默认为5，居中" +
            "1左上、2中上、3右上" +
            "4左中、5居中、6右中" +
            "7左下、8中下、9右下")
    private Integer location = 5;

    @ApiModelProperty(value = "x坐标")
    private Integer x = 0;

    @ApiModelProperty(value = "y坐标")
    private Integer y = 0;

    @ApiModelProperty(value = "是否是文字坐标")
    private Boolean isText = false;

    public void initParam() {
        if (Objects.isNull(location) || location == 0) {
            location = 5;
        }
        if (Objects.isNull(x)) {
            x = 0;
        }
        if (Objects.isNull(y)) {
            y = 0;
        }
    }

    public String build() {
        return OVERLAY +
                FFmpegStrUtils.EQUAL +
                buildXY();
    }

    public String build(Time time, Move move) {
        return OVERLAY +
                FFmpegStrUtils.EQUAL +
                move.build(time, buildX(), buildY());
    }

    public String buildXY() {
        return X_EQ +
                buildX() +
                FFmpegStrUtils.COLON +
                Y_EQ +
                buildY();
    }

    public String buildXY(Time time, Move move) {
        return move.build(time, buildX(), buildY());
    }

    public String buildY() {
        StringBuilder startY = new StringBuilder();
        switch (location) {
            case 1:
            case 2:
            case 3:
                startY.append(ZERO);
                break;
            case 7:
            case 8:
            case 9:
                startY.append(isText ? Y_H_TEXT : Y_H);
                break;
            default:
                startY.append(isText ? Y_CENTER_TEXT : Y_CENTER);
                break;
        }
        return startY.append(buildOffset(y))
                .toString();
    }

    public String buildX() {
        StringBuilder startX = new StringBuilder();
        switch (location) {
            case 1:
            case 4:
            case 7:
                startX.append(ZERO);
                break;
            case 3:
            case 6:
            case 9:
                startX.append(isText ? X_W_TEXT : X_W);
                break;
            default:
                startX.append(isText ? X_CENTER_TEXT : X_CENTER);
                break;
        }
        return startX.append(buildOffset(x))
                .toString();
    }

    public String buildOffset(Integer offset) {
        if (offset >= 0) {
            return FFmpegStrUtils.ADD + offset;
        }
        return String.valueOf(offset);
    }


}
