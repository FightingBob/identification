package com.bob.identification.service;

import com.bob.identification.common.api.QueryCode;
import com.bob.identification.dto.CustomCodeAddParam;
import com.bob.identification.common.api.QueryCodeResult;

/**
 * 自定义防伪码管理接口
 * Created by LittleBob on 2021/1/3/003.
 */

public interface IdentificationCustomCodeService {

    /**
     * 添加防伪码
     *
     * @param customCodeAddParam 防伪码参数
     * @return 添加状态
     */
    QueryCode add(CustomCodeAddParam customCodeAddParam);

    /**
     * 更新防伪码状态
     *
     * @param queryCode 防伪码
     */
    void updateStatus(QueryCode queryCode);


    /**
     * 查询防伪码
     *
     * @param code 防伪码
     * @return 防伪码
     */
    QueryCodeResult query(String code);

    /**
     * 更新防伪码
     *
     * @param queryCode 防伪码
     */
    void update(QueryCode queryCode);
}
