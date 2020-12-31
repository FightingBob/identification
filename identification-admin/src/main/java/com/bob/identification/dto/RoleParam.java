package com.bob.identification.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 角色参数
 * Created by LittleBob on 2020/12/24/024.
 */
@Getter
@Setter
public class RoleParam {

    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称", required = true)
    private String name;

    @NotBlank(message = "描述不能为空")
    @ApiModelProperty(value = "描述", required = true)
    private String description;

    @ApiModelProperty(value = "启动状态", required = true)
    private Integer status;
}
