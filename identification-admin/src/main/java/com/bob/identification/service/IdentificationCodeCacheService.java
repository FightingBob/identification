package com.bob.identification.service;

import com.bob.identification.common.api.QueryCode;

import java.util.List;

/**
 * 防伪码缓存管理接口
 * Created by LittleBob on 2020/12/31/031.
 */
public interface IdentificationCodeCacheService {

    /**
     * 添加防伪码缓存
     * @param batchId 批次id
     * @param preThreeNumber 防伪码前缀三个数字
     * @param list 防伪码
     */
    void addList(Integer batchId, String preThreeNumber, List<String> list);

    /**
     * 获取防伪码
     * @param batchId 批次id
     * @param preThreeNumber 防伪码前缀三个数字
     * @return 防伪码
     */
    List<String> getList(Integer batchId, String preThreeNumber);

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
     * 添加白名单防伪码
     * @param queryCode 防伪码
     */
    void addWhiteCode(QueryCode queryCode);

    /**
     * 添加黑名单防伪码
     * @param queryCode 防伪码
     */
    void addBlackCode(QueryCode queryCode);

    /**
     * 添加防伪码到黑名单
     * @param code 防伪码
     */
    void addBlackCode(String code);

    /**
     * 删除白名单的防伪码
     * @param queryCode 防伪码
     */
    void deleteWhiteCode(QueryCode queryCode);

    /**
     * 删除黑名单的防伪码
     * @param code 防伪码
     */
    void deleteBlackCode(String code);

    /**
     * 删除批次防伪码
     * @param batchId 批次id
     */
    void deleteListByBatchId(Long batchId);
}
