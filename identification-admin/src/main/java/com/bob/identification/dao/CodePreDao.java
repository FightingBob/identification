package com.bob.identification.dao;

import com.bob.identification.identification.po.CodePre;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by LittleBob on 2020/12/30/030.
 */
public interface CodePreDao {

    /**
     * 批量插入防伪码
     * @param pageResult 每页数据
     */
    void batchInsert(@Param("list") List<CodePre> pageResult, @Param("tableName") String tableName);
}
