package com.bob.identification.service;

import com.bob.identification.common.api.QueryCode;
import com.bob.identification.common.api.QueryCodeResult;

/**
 * 旧防伪码管理接口
 * Created by LittleBob on 2021/1/6/006.
 */
public interface IdentificationOldCodeService {

    /**
     * 查询防伪码
     * @param code 防伪码
     * @return 防伪码查询结果
     */
    QueryCodeResult query(String code);

    /**
     * 更新防伪码
     * @param queryCode 防伪码
     */
    void update(QueryCode queryCode);

    /**
     * 更新防伪码状态
     * @param queryCode 防伪码
     */
    void updateStatus(QueryCode queryCode);
}
