package com.bob.identification.common.api;

/**
 * 封装API的错误码
 * Created by LittleBob on 2020/12/16/016.
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
