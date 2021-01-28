package com.bob.identification.service;

import com.bob.identification.common.api.QueryCode;
import com.bob.identification.identification.po.Config;

import java.util.List;

/**
 * 提示管理接口
 * Created by LittleBob on 2021/1/3/003.
 */
public interface IdentificationConfigService {

    /**
     * 根据防伪码查询提示
     * @param queryCode 防伪码
     * @return 提示
     */
    String getConfigByQueryCode(QueryCode queryCode);

    /**
     * 获取错误提示
     * @param code 防伪码
     * @return 错误提示
     */
    String getErrorMessage(String code);

    /**
     * 添加提示
     * @param config 提示
     * @return 添加状态
     */
    int add(Config config);

    /**
     * 提示列表
     * @return 提示列表
     */
    List<Config> list();

    /**
     * 删除提示
     * @param configId 提示id
     * @return 删除状态
     */
    int delete(Long configId);

    /**
     * 更新提示
     * @param config 提示
     * @return 更新状态
     */
    int update(Config config);
}
