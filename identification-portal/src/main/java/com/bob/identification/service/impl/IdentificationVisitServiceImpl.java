package com.bob.identification.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.bob.identification.common.domain.VisitDto;
import com.bob.identification.common.domain.Visitor;
import com.bob.identification.identification.mapper.VisitMapper;
import com.bob.identification.identification.po.Visit;
import com.bob.identification.service.IPCacheService;
import com.bob.identification.service.IdentificationVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 访问管理接口实现类
 * Created by LittleBob on 2021/1/4/004.
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
@DS("slave_2")
public class IdentificationVisitServiceImpl implements IdentificationVisitService {

    @Autowired
    private VisitMapper visitMapper;

    @Autowired
    private IPCacheService ipCacheService;

    @Override
    public void addDayVisitRecord() {
        VisitDto visitDto = ipCacheService.getYesterdayVisit();
        if (BeanUtil.isNotEmpty(visitDto)) {
            Visitor visitor = ipCacheService.getYesterdayBestVisitor();
            if (BeanUtil.isNotEmpty(visitor)) {
                Visit visit = new Visit();
                BeanUtil.copyProperties(visitDto, visit);
                visit.setOftenVisitIp(visitor.getIpAddress());
                visit.setOftenVisitTimes(visitor.getVisitTimes());
                LocalDateTime parse = LocalDateTime.parse(DateUtil.formatDate(DateUtil.yesterday()) + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                visit.setCreateTime(parse);
                visitMapper.insert(visit);
            }
        }
    }

    @Override
    public void operateIP(String ipAddress) {
        Visitor visitor = ipCacheService.getVisitor(ipAddress);
        if (BeanUtil.isEmpty(visitor)) {
            addVisitor(ipAddress);
            addOneByVisitRecord();
        } else {
            updateVisitor(visitor);
            updateVisitRecord();
        }
    }

    @Override
    public void deleteYesterdayVisit() {
        ipCacheService.deleteYesterdayVisitor();
        ipCacheService.deleteYesterdayVisitRecord();
    }

    /**
     * 更新访问对象
     * @param visitor 访问对象
     */
    private void updateVisitor(Visitor visitor) {
        visitor.setVisitTimes(visitor.getVisitTimes() + 1);
        ipCacheService.addVisitor(visitor);
    }

    /**
     * 更新访问记录
     */
    private void updateVisitRecord() {
        VisitDto visit = ipCacheService.getVisitRecord();
        if (BeanUtil.isNotEmpty(visit)) {
            visit.setVisitTimes(visit.getVisitTimes() + 1);
            ipCacheService.addVisitRecord(visit);
        } else {
           addVisitRecord();
        }
    }

    /**
     * 添加访问对象
     * @param ipAddress IP地址
     */
    private void addVisitor(String ipAddress) {
        Visitor visitor = new Visitor();
        visitor.setIpAddress(ipAddress);
        visitor.setVisitTimes(1);
        ipCacheService.addVisitor(visitor);
    }

    /**
     * 访问人数加1
     */
    private void addOneByVisitRecord() {
        VisitDto visit = ipCacheService.getVisitRecord();
        if (BeanUtil.isEmpty(visit)) {
            addVisitRecord();
        } else {
            visit.setVisitNumber(visit.getVisitNumber() + 1);
            visit.setVisitTimes(visit.getVisitTimes() + 1);
            ipCacheService.addVisitRecord(visit);
        }

    }

    private void addVisitRecord() {
        VisitDto visit = new VisitDto();
        visit.setVisitTimes(1);
        visit.setVisitNumber(1);
        ipCacheService.addVisitRecord(visit);
    }
}
