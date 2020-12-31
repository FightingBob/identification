package com.bob.identification.common.util;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.bob.identification.common.exception.Asserts;

/**
 * 校验工具
 * Created by LittleBob on 2020/12/22/022.
 */
public class ValidatorUtil {

    public static void  isEmail(String email) {
        if (StrUtil.isNotBlank(email) && !Validator.isEmail(email)) {
            Asserts.fail("邮箱格式错误");
        }
    }

    public static void  isPhone(String phone) {
        if (!Validator.isMobile(phone)) {
            Asserts.fail("手机号格式错误");
        }
    }

    public static void isUsername(String username) {
        if (!Validator.isGeneral(username, 6)) {
            Asserts.fail("账号格式错误，账号应符合英文字母 、数字和下划线三种组合，长度超过6位数");
        }
    }
}
