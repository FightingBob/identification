package com.bob.identification.service;

import com.bob.identification.identification.po.OldBatch;

/**
 * 旧防伪批次管理接口
 * Created by LittleBob on 2021/1/6/006.
 */
public interface IdentificationOldBatchService {

    /**
     * 根据防伪码前缀前三个数字获取批次
     * @param preThreeNumber 前缀三个数字
     * @return 旧批次
     */
    OldBatch getByPreThreeNumber(String preThreeNumber);
}
