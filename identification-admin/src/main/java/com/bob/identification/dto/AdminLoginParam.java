package com.bob.identification.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录参数
 * Created by LittleBob on 2020/12/22/022.
 */
@Getter
@Setter
public class AdminLoginParam {

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    @Length(min = 4, message = "用户名格式错误，长度不足4位数")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    @Length(min = 6 , message = "密码格式错误，长度不足6位数")
    private String password;
}
