package com.bob.identification.service;

/**
 * 访问管理接口
 * Created by LittleBob on 2021/1/4/004.
 */
public interface IdentificationVisitService {
    /**
     * 将每天的访问记录导入数据库
     */
    void addDayVisitRecord();

    /**
     * 处理IP地址
     * @param ipAddress ip地址
     */
    void operateIP(String ipAddress);

    /**
     * 删除昨天的访问记录
     */
    void deleteYesterdayVisit();
}
