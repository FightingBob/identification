package com.bob.identification.common.exception;

import com.bob.identification.common.api.IErrorCode;

/**
 * 断言处理类,用于抛出各种API异常
 * Created by LittleBob on 2020/12/17/017.
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
