package com.bob.identification.service;

import com.bob.identification.identification.po.Batch;

import java.util.List;

/**
 * 防伪码管理接口
 * Created by LittleBob on 2020/12/30/030.
 */
public interface IdentificationCodeService {

    /**
     * 添加防伪码
     *
     * @param suffix 数据表后缀
     */
    void laikouAddCode(String suffix);

    /**
     * 添加莱蔻一个批次的防伪码
     *
     * @param batch 批次
     * @return 添加状态
     */
    int laikouAddList(Batch batch);

    /**
     * 添加防伪码
     *
     * @param suffix 数据表后缀
     */
    void fenyiAddCode(String suffix);

    /**
     * 添加芬逸一个批次的防伪码
     *
     * @param batch 批次
     * @return 添加状态
     */
    int fenyiAddList(Batch batch);

    /**
     * 根据批次id获取莱蔻防伪码
     * @param batch 批次
     * @return 防伪码
     */
    List<String> laikouGetListByBatchId(Batch batch);

    /**
     * 根据批次id获取芬逸防伪码
     * @param batch 批次
     * @return 防伪码
     */
    List<String> fenyiGetListByBatchId(Batch batch);
}
