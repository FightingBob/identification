package com.bob.identification.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 添加品牌参数
 * Created by LittleBob on 2020/12/29/029.
 */
@Getter
@Setter
public class AddBrandParam {

    @ApiModelProperty(value = "防伪码前缀", required = true)
    @NotBlank(message = "防伪码前缀不能为空")
    private String preNumber;

    @ApiModelProperty(value = "品牌名称", required = true)
    @NotBlank(message = "品牌名称不能为空")
    private String name;

    @ApiModelProperty(value = "品牌代号", required = true)
    @NotBlank(message = "品牌代号不能为空")
    private String brandCode;
}
