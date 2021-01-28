package com.bob.identification.service;

import com.bob.identification.identification.po.Brand;

/**
 * 防伪品牌管理接口
 * Created by LittleBob on 2021/1/4/004.
 */
public interface IdentificationBrandService {

    /**
     * 获取品牌
     * @param brandId 品牌id
     * @return 品牌
     */
    Brand getById(Integer brandId);

    /**
     * 根据前缀获取品牌
     * @param preNumber 前缀
     * @return 品牌
     */
    Brand getByPreNumber(Integer preNumber);
}
