package com.bob.identification.controller;

import com.bob.identification.common.api.CommonResult;
import com.bob.identification.service.IdentificationVisitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前台访问管理
 * Created by LittleBob on 2020/12/29/029.
 */
@Api(tags = "IdentificationVisitController", description = "前台访问管理")
@RequestMapping("/visit")
@RestController
public class IdentificationVisitController {

    @Autowired
    private IdentificationVisitService visitService;

    @ApiOperation(value = "访问人数")
    @GetMapping("/visitNumber")
    public CommonResult visitNumber() {
        Integer visitNumber = visitService.getVisitNumber();
        return CommonResult.success(visitNumber);
    }

}
