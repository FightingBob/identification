package com.bob.identification.service;

import com.bob.identification.dto.AddBrandParam;
import com.bob.identification.identification.po.Brand;

import java.util.List;

/**
 * 防伪品牌管理接口
 * Created by LittleBob on 2020/12/29/029.
 */
public interface IdentificationBrandService {

    /**
     * 获取品牌列表
     * @return 品牌列表
     */
    List<Brand> list();

    /**
     * 添加品牌
     * @param addBrandParam 参数
     * @return 添加状态
     */
    int add(AddBrandParam addBrandParam);

    /**
     * 更新品牌
     * @param brandId 品牌id
     * @param brand 品牌
     * @return 更新状态
     */
    int update(Integer brandId, Brand brand);

    /**
     * 删除品牌
     * @param brandId 品牌id
     * @return 删除状态
     */
    int delete(Integer brandId);

    /**
     * 根据前缀获取品牌
     * @param preNumber 前缀
     * @return 品牌
     */
    Brand getByPreNumber(Integer preNumber);
}
