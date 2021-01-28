package com.bob.identification.common.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.bob.identification.common.exception.Asserts;

/**
 * 字符串工具类
 * Created by LittleBob on 2021/1/3/003.
 */
public class MyStrUtil {

    public static Integer getStrByIndex(String str, Integer index, Integer length) {
        return Integer.parseInt(StrUtil.subWithLength(str, index, length));
    }

    /**
     * 获取字符串第一个数字
     * @param str 字符串
     * @return 第一个数字
     */
    public static Integer getFirstNumber(String str) {
        return getStrByIndex(str, 0, 1);
    }

    /**
     * 获取字符串第二个数字
     * @param str 字符串
     * @return 第二个数字
     */
    public static Integer getSecondNumber(String str) {
        return getStrByIndex(str, 1, 1);
    }

    /**
     * 获取字符串前缀前三个数字
     * @param str 字符串
     * @return 前三个数字
     */
    public static String getPreThreeNumber(String str) {
        return StrUtil.subWithLength(str, 0, 3);
    }

    /**
     * 校验防伪码
     * @param serialNumber 防伪码
     */
    public static boolean validateSerialNumber(String serialNumber) {
        return BeanUtil.isEmpty(serialNumber) || serialNumber.length() != 15 || !Validator.isNumber(serialNumber);
    }
}
