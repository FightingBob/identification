package com.bob.identification.common.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * id生成工具
 * Created by LittleBob on 2020/12/31/031.
 */
public class MyIdUtil {

    private static String createSnowflake(long workerId, long dataCenterId) {
        return IdUtil.createSnowflake(workerId, dataCenterId).nextIdStr();
    }

    private static long getRandomNumber() {
        return RandomUtil.randomLong(1, 9);
    }

    public static String getSnowflake() {
        return createSnowflake(getRandomNumber(), getRandomNumber());
    }

}
