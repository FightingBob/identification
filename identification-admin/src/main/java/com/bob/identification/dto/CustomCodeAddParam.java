package com.bob.identification.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加自定义防伪码参数
 * Created by LittleBob on 2021/1/3/003.
 */
@Getter
@Setter
public class CustomCodeAddParam {

    @ApiModelProperty(value = "防伪码", required = true)
    @NotBlank(message = "防伪码不能为空")
    private String serialNumber;

    @ApiModelProperty(value = "品牌id", required = true)
    @NotNull(message = "品牌不能为空")
    private Integer brandId;
}
