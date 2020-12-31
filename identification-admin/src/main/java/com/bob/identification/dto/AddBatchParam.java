package com.bob.identification.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 添加批次参数
 * Created by LittleBob on 2020/12/29/029.
 */
@Getter
@Setter
public class AddBatchParam {

    @ApiModelProperty(value = "防伪码前缀不能为空", required = true)
    @NotNull(message = "防伪码前缀不能为空")
    private Integer firstNumber;

    @ApiModelProperty(value = "数量不能为空", required = true)
    @NotNull(message = "数量不能为空")
    private Integer demand;
}
