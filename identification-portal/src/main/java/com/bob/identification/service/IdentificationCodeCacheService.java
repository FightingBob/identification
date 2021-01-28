package com.bob.identification.service;

import com.bob.identification.common.api.QueryCode;

/**
 * 防伪码缓存管理接口
 * Created by LittleBob on 2021/1/4/004.
 */
public interface IdentificationCodeCacheService {

    /**
     * 从白名单里查询防伪码
     * @param code 防伪码
     * @return 防伪码
     */
    QueryCode queryCodeFromWhiteList(String code);

    /**
     * 从黑名单里查询防伪码
     * @param code 防伪码
     * @return 防伪码
     */
    QueryCode queryCodeFromBlackList(String code);

    /**
     * 添加黑名单防伪码
     * @param queryCode 防伪码
     */
    void addBlackCode(QueryCode queryCode);

    /**
     * 删除白名单的防伪码
     * @param queryCode 防伪码
     */
    void deleteWhiteCode(QueryCode queryCode);

    /**
     * 添加白名单防伪码
     * @param queryCode 防伪码
     */
    void addWhiteCode(QueryCode queryCode);

    /**
     * 添加防伪码到黑名单
     * @param code 防伪码
     */
    void addBlackCode(String code);
}
