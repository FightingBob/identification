package com.bob.identification.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 用户更新密码参数
 * Created by LittleBob on 2020/12/22/022.
 */
@Getter
@Setter
public class AdminUpdatePasswordParam {

    @ApiModelProperty(value = "旧密码", required = true)
    @NotBlank(message = "旧密码不能为空")
    @Length(min = 6 , message = "旧密码格式错误，长度不足6位数")
    private String oldPassword;

    @ApiModelProperty(value = "新密码", required = true)
    @NotBlank(message = "新密码不能为空")
    @Length(min = 6 , message = "新密码格式错误，长度不足6位数")
    private String newPassword;
}
