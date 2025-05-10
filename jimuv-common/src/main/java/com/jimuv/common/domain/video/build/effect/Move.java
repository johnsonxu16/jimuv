package com.jimuv.common.domain.video.build.effect;

import com.jimuv.common.domain.video.build.effect.move.In;
import com.jimuv.common.domain.video.build.effect.move.Out;
import com.jimuv.common.util.FFmpegStrUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Accessors(chain = true)
@ApiModel(value = "移动特效")
public class Move {

    private static final Integer MOVE_LEFT = 1;
    private static final Integer MOVE_RIGHT = 2;
    private static final Integer MOVE_UP = 3;
    private static final Integer MOVE_DOWN = 4;

    private static final String X_EQ = "x=";
    private static final String Y_EQ = "y=";

    @ApiModelProperty(value = "入场")
    private In in;

    @ApiModelProperty(value = "出场")
    private Out out;

    public String build(Time time, String overlayX, String overlayY) {
        if (Objects.nonNull(in) && in.getMoveTime().compareTo(BigDecimal.ZERO) > 0 && Objects.nonNull(out) && out.getMoveTime().compareTo(BigDecimal.ZERO) > 0) {
            if (MOVE_LEFT.equals(in.getMoveDirection()) || MOVE_RIGHT.equals(in.getMoveDirection())) {
                overlayX = "'if(between(t," + time.getStartTime() + "," + time.getStartTime().add(in.getMoveTime()) + ")," + (overlayX + buildMoveDirectionSymbol(in.getMoveDirection()) + "(" + in.getMoveSpeed() + "*(" + time.getStartTime().add(in.getMoveTime()) + "-t))") + ",if(between(t," + time.getEndTime().subtract(out.getMoveTime()) + "," + time.getEndTime() + ")," + (overlayX + buildMoveDirectionSymbol(out.getMoveDirection()) + "(" + out.getMoveSpeed() + "*(" + time.getEndTime().subtract(out.getMoveTime()) + "-t))") + "," + overlayX + "))'";
            }
            if (MOVE_UP.equals(in.getMoveDirection()) || MOVE_DOWN.equals(in.getMoveDirection())) {
                overlayY = "'if(between(t," + time.getStartTime() + "," + time.getStartTime().add(in.getMoveTime()) + ")," + (overlayY + buildMoveDirectionSymbol(in.getMoveDirection()) + "(" + in.getMoveSpeed() + "*(" + time.getStartTime().add(in.getMoveTime()) + "-t))") + ",if(between(t," + time.getEndTime().subtract(out.getMoveTime()) + "," + time.getEndTime() + ")," + (overlayY + buildMoveDirectionSymbol(out.getMoveDirection()) + "(" + out.getMoveSpeed() + "*(" + time.getEndTime().subtract(out.getMoveTime()) + "-t))") + "," + overlayY + "))'";
            }
        } else {
            if (Objects.nonNull(in) && in.getMoveTime().compareTo(BigDecimal.ZERO) > 0) {
                if (MOVE_LEFT.equals(in.getMoveDirection()) || MOVE_RIGHT.equals(in.getMoveDirection())) {
                    overlayX = "'if(between(t," + time.getStartTime() + "," + time.getStartTime().add(in.getMoveTime()) + ")," + (overlayX + buildMoveDirectionSymbol(in.getMoveDirection()) + "(" + in.getMoveSpeed() + "*(" + time.getStartTime().add(in.getMoveTime()) + "-t))") + "," + overlayX + ")'";
                }
                if (MOVE_UP.equals(in.getMoveDirection()) || MOVE_DOWN.equals(in.getMoveDirection())) {
                    overlayY = "'if(between(t," + time.getStartTime() + "," + time.getStartTime().add(in.getMoveTime()) + ")," + (overlayY + buildMoveDirectionSymbol(in.getMoveDirection()) + "(" + in.getMoveSpeed() + "*(" + time.getStartTime().add(in.getMoveTime()) + "-t))") + "," + overlayY + ")'";
                }
            }
            if (Objects.nonNull(out) && out.getMoveTime().compareTo(BigDecimal.ZERO) > 0) {
                if (MOVE_LEFT.equals(in.getMoveDirection()) || MOVE_RIGHT.equals(in.getMoveDirection())) {
                    overlayX = "'if(between(t," + time.getEndTime().subtract(out.getMoveTime()) + "," + time.getEndTime() + ")," + (overlayX + buildMoveDirectionSymbol(out.getMoveDirection()) + "(" + out.getMoveSpeed() + "*(" + time.getEndTime().subtract(out.getMoveTime()) + "-t))") + "," + overlayX + ")'";
                }
                if (MOVE_UP.equals(in.getMoveDirection()) || MOVE_DOWN.equals(in.getMoveDirection())) {
                    overlayY = "'if(between(t," + time.getEndTime().subtract(out.getMoveTime()) + "," + time.getEndTime() + ")," + (overlayY + buildMoveDirectionSymbol(out.getMoveDirection()) + "(" + out.getMoveSpeed() + "*(" + time.getEndTime().subtract(out.getMoveTime()) + "-t))") + "," + overlayY + ")'";
                }
            }
        }
        return buildOverlayXY(overlayX, overlayY);
    }

    private String buildOverlayXY(String overlayX, String overlayY) {
        return X_EQ +
                overlayX +
                FFmpegStrUtils.COLON +
                Y_EQ +
                overlayY;
    }

    private String buildMoveDirectionSymbol(Integer moveDirection) {
        if (MOVE_LEFT.equals(moveDirection) || MOVE_UP.equals(moveDirection)) {
            return FFmpegStrUtils.ADD;
        }
        if (MOVE_RIGHT.equals(moveDirection) || MOVE_DOWN.equals(moveDirection)) {
            return FFmpegStrUtils.REDUCE;
        }
        return FFmpegStrUtils.ADD;
    }

}
