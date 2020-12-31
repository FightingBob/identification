package com.bob.identification.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

/**
 * 用户注册参数
 * Created by LittleBob on 2020/12/18/018.
 */
@Getter
@Setter
public class AdminRegisterParam {

    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    @Length(min = 6, message = "密码格式错误，长度不足6位数")
    private String password;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名")
    private String nickname;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机格式错误")
    private String phone;
}
