package com.bob.identification.service;

import com.bob.identification.common.api.QueryCode;

/**
 * 防伪码提示管理接口
 * Created by LittleBob on 2021/1/4/004.
 */
public interface IdentificationConfigService {

    /**
     * 获取错误提示
     * @param code 防伪码
     * @return 错误提示
     */
    String getErrorMessage(String code);

    /**
     * 根据防伪码查询提示
     * @param queryCode 防伪码
     * @return 提示
     */
    String getConfigByQueryCode(QueryCode queryCode);
}
