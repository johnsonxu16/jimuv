package com.jimuv.common.domain.video.build.effect;

import com.jimuv.common.util.FFmpegStrUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Accessors(chain = true)
@ApiModel(value = "圆角设置")
public class Radius {

    @ApiModelProperty(value = "圆角弧度")
    private BigDecimal radian = BigDecimal.ZERO;

    public String build() {
        if (Objects.nonNull(radian) && radian.compareTo(BigDecimal.ZERO) > 0) {
            return FFmpegStrUtils.COMMA + "geq=r='r(X,Y)':g='g(X,Y)':b='b(X,Y)':a='if(gte(abs(X-W/2),W/2-" + radian + ")*gte(abs(Y-H/2),H/2-" + radian + "),if(lte(hypot(" + radian + "-(W/2-abs(X-W/2))," + radian + "-(H/2-abs(Y-H/2)))," + radian + "),alpha(X,Y),0),alpha(X,Y))'";
        }
        return FFmpegStrUtils.EMPTY;
    }

}
