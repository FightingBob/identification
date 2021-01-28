package com.bob.identification.common.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * 防伪码查询对象
 * Created by LittleBob on 2021/1/3/003.
 */
@Getter
@Setter
public class QueryCode {

    private Long id;

    /**
     * 防伪码批次
     */
    private Integer batchId;

    /**
     * 防伪编号
     */
    private String serialNumber;

    /**
     * 查询次数
     */
    private Integer queryTime;

    /**
     * 防伪码状态，1有效，0无效
     */
    private Integer status;

    private Integer databaseOption;

    private Integer tableOption;

    /**
     * 是否是真的防伪码
     */
    private int isReal;
}
