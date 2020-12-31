package com.bob.identification.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 更新用户信息参数
 * Created by LittleBob on 2020/12/22/022.
 */
@Getter
@Setter
public class AdminUpdateInfoParam {

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "账号不能为空")
    private String username;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "备注")
    private String note;
}
