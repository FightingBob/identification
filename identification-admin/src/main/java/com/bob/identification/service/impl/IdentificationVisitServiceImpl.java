package com.bob.identification.service.impl;

import com.bob.identification.service.IdentificationVisitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 前台访问管理接口实现类
 * Created by LittleBob on 2020/12/29/029.
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class IdentificationVisitServiceImpl implements IdentificationVisitService {

    @Override
    public Integer getVisitNumber() {
        // TODO: 2020/12/29/029 还未开发
        return null;
    }


}
