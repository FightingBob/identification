package com.bob.identification.service;

import com.bob.identification.common.api.QueryCode;
import com.bob.identification.common.api.QueryCodeResult;

/**
 * 防伪码管理接口
 * Created by LittleBob on 2021/1/4/004.
 */
public interface IdentificationCodeService {

    /**
     * 从缓存中查询防伪码
     * @param code 防伪码
     * @return 防伪码
     */
    QueryCodeResult queryCodeFromCache(String code);

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
     *校验防伪码
     * @param code 防伪码
     */
    void validateCode(String code);

    /**
     * 配置防伪码查询结果
     * @param queryCode 防伪码
     * @return 防伪码查询结果
     */
    QueryCodeResult setQueryCodeResult(QueryCode queryCode);

    /**
     * 获取错误防伪码查询结果
     * @param code 防伪码
     * @return 防伪码结果
     */
    QueryCodeResult getErrorQueryCodeResult(String code);
}
