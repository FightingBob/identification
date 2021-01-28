package com.bob.identification.service;

import com.bob.identification.common.domain.VisitDto;
import com.bob.identification.common.domain.Visitor;

/**
 * IP缓存管理接口
 * Created by LittleBob on 2021/1/4/004.
 */
public interface IPCacheService {

    /**
     * 获取昨天的访问记录
     * @return 访问记录
     */
    VisitDto getYesterdayVisit();

    /**
     * 获取昨天最频繁访问的用户
     * @return 用户
     */
    Visitor getYesterdayBestVisitor();

    /**
     * 获取访问对象
     * @param ipAddress IP地址
     * @return 访问对象
     */
    Visitor getVisitor(String ipAddress);

    /**
     * 添加访问对象
     * @param visitor 访问对象
     */
    void addVisitor(Visitor visitor);

    /**
     * 获取访问记录
     * @return 访问记录
     */
    VisitDto getVisitRecord();

    /**
     * 添加访问记录
     * @param visit 访问记录
     */
    void addVisitRecord(VisitDto visit);

    /**
     * 删除昨天的访问对象
     */
    void deleteYesterdayVisitor();

    /**
     * 删除昨天访问记录
     */
    void deleteYesterdayVisitRecord();
}
