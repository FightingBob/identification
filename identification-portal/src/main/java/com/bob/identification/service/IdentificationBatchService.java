package com.bob.identification.service;

import com.bob.identification.identification.po.Batch;

/**
 * 防伪批次管理接口
 * Created by LittleBob on 2021/1/4/004.
 */
public interface IdentificationBatchService {

    /**
     * 根据防伪码前三数字获取批次
     * @param preThreeNumber 前三个数字
     * @return 批次
     */
    Batch getByPreThreeNumber(String preThreeNumber);
}
