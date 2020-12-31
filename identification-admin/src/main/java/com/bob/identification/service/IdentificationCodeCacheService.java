package com.bob.identification.service;

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
}
