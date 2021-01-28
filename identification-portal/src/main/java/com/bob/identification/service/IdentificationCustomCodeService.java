package com.bob.identification.service;

import com.bob.identification.common.api.QueryCode;
import com.bob.identification.common.api.QueryCodeResult;

/**
 * 自定义防伪码管理接口
 * Created by LittleBob on 2021/1/4/004.
 */
public interface IdentificationCustomCodeService {

    /**
     * 查询防伪码
     * @param code 防伪码
     * @return 防伪码
     */
    QueryCodeResult query(String code);

    /**
     * 更新防伪码
     * @param queryCode 防伪码
     */
    void update(QueryCode queryCode);
}
