package com.bob.identification.controller;

import com.bob.identification.common.api.CommonPage;
import com.bob.identification.common.api.CommonResult;
import com.bob.identification.common.util.IPUtil;
import com.bob.identification.dto.AddBatchParam;
import com.bob.identification.service.IdentificationBatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 防伪批次管理
 * Created by LittleBob on 2020/12/29/029.
 */
@Api(tags = "IdentificationBatchController", description = "防伪批次管理")
@RestController
@RequestMapping("/batch")
public class IdentificationBatchController {

    @Autowired
    private IdentificationBatchService batchService;

    @ApiOperation("新增批次")
    @PostMapping("/add")
    public CommonResult add(@Validated @RequestBody AddBatchParam addBatchParam) {
        //int count = batchService.add(addBatchParam);
        //return count > 0 ? CommonResult.success(null) : CommonResult.failed();
        batchService.add(addBatchParam);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "查询批次列表")
    @GetMapping("/list")
    public CommonResult list(@RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage commonPage = batchService.list(keyword, pageSize, pageNum);
        return CommonResult.success(commonPage);
    }

    @ApiOperation(value = "修改批次状态")
    @PostMapping("/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Integer id,
                                     @RequestParam(value = "status", required = true) Integer status) {
        int count = batchService.updateStatus(id, status);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation("删除批次")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam Long batchId) {
        int count = batchService.delete(batchId);
        return count > 0 ? CommonResult.success(null) : CommonResult.failed();
    }

    @ApiOperation("生成防伪码文件")
    @PostMapping("/createFile")
    public CommonResult createFile(@RequestParam Integer batchId) {
        batchService.createFile(batchId);
        return CommonResult.success(null);
    }

    @ApiOperation("导出txt文件")
    @GetMapping("/export")
    public void exportBatch(@RequestParam Integer batchId, @RequestParam String code, HttpServletResponse response) {
        batchService.exportFile(batchId, code, response);
    }

    @ApiOperation("获取ip地址")
    @GetMapping("/ip")
    public CommonResult getIP(HttpServletRequest request) {
        System.out.println(IPUtil.getIpAddress(request));
        return CommonResult.success(IPUtil.getIpAddress(request));
    }

    @ApiOperation("删除文件")
    @PostMapping("/deleteFile")
    public CommonResult deleteFile(@RequestParam Integer batchId) {
        int count = batchService.deleteFile(batchId);
        return count > 0 ? CommonResult.success(null) : CommonResult.failed();
    }
}
