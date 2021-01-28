package com.bob.identification.service;

import com.bob.identification.common.api.QueryCode;
import com.bob.identification.common.api.QueryCodeResult;
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

    /**
     * 更新莱蔻防伪码查询次数
     * @param queryCode 防伪码
     */
    void laikouUpdate(QueryCode queryCode);


    /**
     * 更新芬逸防伪码查询次数
     * @param queryCode 防伪码
     */
    void fenyiUpdate(QueryCode queryCode);

    /**
     * 更新凯宾防伪码查询次数
     * @param queryCode 防伪码
     */
    void kaibinUpdate(QueryCode queryCode);

    /**
     * 更新雅莱蒂诗防伪码查询次数
     * @param queryCode 防伪码
     */
    void yaladysUpdate(QueryCode queryCode);

    /**
     * 从缓存中查询防伪码
     * @param code 防伪码
     * @return 防伪码
     */
    QueryCodeResult queryCodeFromCache(String code);

    /**
     * 配置防伪码查询结果
     * @param queryCode 防伪码
     * @return 防伪码查询结果
     */
    QueryCodeResult setQueryCodeResult(QueryCode queryCode);

    /**
     * 查询莱蔻防伪码
     * @param code 防伪码
     * @param secondNumber 防伪码前缀第二个数字
     * @return 防伪码
     */
    QueryCodeResult laikouQuery(String code, Integer secondNumber);

    /**
     * 查询芬逸防伪码
     * @param code 防伪码
     * @param secondNumber 防伪码前缀第二个数字
     * @return 防伪码
     */
    QueryCodeResult fenyiQuery(String code, Integer secondNumber);

    /**
     * 查询凯宾防伪码
     * @param code 防伪码
     * @param secondNumber 防伪码前缀第二个数字
     * @return 防伪码
     */
    QueryCodeResult kaibinQuery(String code, Integer secondNumber);

    /**
     * 查询雅莱蒂诗防伪码
     * @param code 防伪码
     * @param secondNumber 防伪码前缀第二个数字
     * @return 防伪码
     */
    QueryCodeResult yaladysQuery(String code, Integer secondNumber);

    /**
     * 更新防伪码状态
     * @param queryCode 防伪码
     */
    void laikouUpdateStatus(QueryCode queryCode);

    /**
     * 更新防伪码状态
     * @param queryCode 防伪码
     */
    void fenyiUpdateStatus(QueryCode queryCode);

    /**
     * 更新防伪码状态
     * @param queryCode 防伪码
     */
    void kaibinUpdateStatus(QueryCode queryCode);

    /**
     * 更新防伪码状态
     * @param queryCode 防伪码
     */
    void yaladysUpdateStatus(QueryCode queryCode);

    /**
     *校验防伪码
     * @param code 防伪码
     */
    void validateCode(String code);

    /**
     * 获取错误防伪码查询结果
     * @param code 防伪码
     * @return 防伪码结果
     */
    QueryCodeResult getErrorQueryCodeResult(String code);

    /**
     * 添加凯宾一个批次的防伪码
     *
     * @param batch 批次
     * @return 添加状态
     */
    int kaibinAddList(Batch batch);

    /**
     * 添加雅莱蒂诗一个批次的防伪码
     *
     * @param batch 批次
     * @return 添加状态
     */
    int yaladysAddList(Batch batch);

    /**
     * 根据批次id获取凯宾防伪码
     * @param batch 批次
     * @return 防伪码
     */
    List<String> kaibinGetListByBatchId(Batch batch);

    /**
     * 根据批次id获取雅莱蒂诗防伪码
     * @param batch 批次
     * @return 防伪码
     */
    List<String> yaladysGetListByBatchId(Batch batch);

    /**
     * 删除缓存中的防伪码，根据批次
     * @param batchId 批次id
     */
    void deleteCacheCodeByBatchId(Long batchId);
}
