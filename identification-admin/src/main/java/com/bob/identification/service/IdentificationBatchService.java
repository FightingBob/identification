package com.bob.identification.service;

import com.bob.identification.common.api.CommonPage;
import com.bob.identification.dto.AddBatchParam;
import com.bob.identification.identification.po.Batch;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 防伪批次管理接口
 * Created by LittleBob on 2020/12/29/029.
 */
public interface IdentificationBatchService {
    /**
     * 添加批次
     *
     * @param addBatchParam 批次参数
     * @return 添加状态
     */
    void add(AddBatchParam addBatchParam);

    /**
     * 查询批次列表
     *
     * @param keyword  关键词
     * @param pageSize 每页数量
     * @param pageNum  页号
     * @return 批次列表
     */
    CommonPage list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改批次状态
     * @param id 批次id
     * @param status 状态
     * @return 更新状态
     */
    int updateStatus(Integer id, Integer status);

    /**
     * 删除批次
     * @param batchId 批次id
     * @return 删除状态
     */
    int delete(Long batchId);

    /**
     * 生成防伪码文件
     * @param batchId 批次id
     * @return 生成状态
     */
    void createFile(Integer batchId);

    /**
     * 导出tx文件
     * @param batchId 批次id
     * @param code 防伪码
     * @param response 响应
     */
    void exportFile(Integer batchId, String code, HttpServletResponse response);

    /**
     * 删除txt文件
     * @param batchId 批次id
     * @return 删除状态
     */
    int deleteFile(Integer batchId);

    /**
     * 根据防伪码前三数字获取批次
     * @param preThreeNumber 前三个数字
     * @return 批次
     */
    Batch getByPreThreeNumber(String preThreeNumber);
}
