package com.hrm.controller;

import com.hrm.common.Result;
import com.hrm.entity.Contract;
import com.hrm.service.ContractService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/api/contract")
public class ContractController {
    @Resource
    private ContractService contractService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(contractService.getById(id));
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) Long employeeId,
                          @RequestParam(required = false) String employeeName,
                          @RequestParam(required = false) String type,
                          @RequestParam(required = false) String status,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(contractService.getList(employeeId, employeeName, type, status, pageNum, pageSize));
    }

    @GetMapping("/employee/{employeeId}")
    public Result getByEmployeeId(@PathVariable Long employeeId) {
        return Result.success(contractService.getByEmployeeId(employeeId));
    }

    @PostMapping
    public Result add(@RequestBody Contract contract) {
        contractService.add(contract);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Contract contract) {
        contractService.update(contract);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        contractService.delete(id);
        return Result.success();
    }

    @PostMapping("/terminate/{id}")
    public Result terminate(@PathVariable Long id) {
        contractService.terminate(id);
        return Result.success();
    }
}
