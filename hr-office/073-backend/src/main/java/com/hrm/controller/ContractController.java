package com.hrm.controller;

import com.hrm.common.BusinessException;
import com.hrm.common.Result;
import com.hrm.entity.Contract;
import com.hrm.entity.User;
import com.hrm.service.ContractService;
import com.hrm.service.UserService;
import com.hrm.utils.AuthUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/contract")
public class ContractController {
    @Resource
    private ContractService contractService;
    @Resource
    private UserService userService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id, HttpServletRequest request) {
        Contract contract = contractService.getById(id);
        requireOwnContractOrHr(request, contract);
        return Result.success(contract);
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) Long employeeId,
                          @RequestParam(required = false) String employeeName,
                          @RequestParam(required = false) String type,
                          @RequestParam(required = false) String status,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          HttpServletRequest request) {
        if (!AuthUtils.isAdminOrHr(request)) {
            employeeId = currentEmployeeId(request);
            employeeName = null;
        }
        return Result.success(contractService.getList(employeeId, employeeName, type, status, pageNum, pageSize));
    }

    @GetMapping("/employee/{employeeId}")
    public Result getByEmployeeId(@PathVariable Long employeeId, HttpServletRequest request) {
        if (!AuthUtils.isAdminOrHr(request) && !employeeId.equals(currentEmployeeId(request))) {
            throw new BusinessException(403, "只能访问自己的合同记录");
        }
        return Result.success(contractService.getByEmployeeId(employeeId));
    }

    @PostMapping
    public Result add(@RequestBody Contract contract, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        contractService.add(contract);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Contract contract, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        contractService.update(contract);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        contractService.delete(id);
        return Result.success();
    }

    @PostMapping("/terminate/{id}")
    public Result terminate(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdminOrHr(request);
        contractService.terminate(id);
        return Result.success();
    }

    private Long currentEmployeeId(HttpServletRequest request) {
        User user = userService.getById(AuthUtils.currentUserId(request));
        if (user == null || user.getEmployeeId() == null) {
            throw new BusinessException(403, "当前账号未绑定员工档案");
        }
        return user.getEmployeeId();
    }

    private void requireOwnContractOrHr(HttpServletRequest request, Contract contract) {
        if (contract == null) {
            throw new BusinessException(404, "合同记录不存在");
        }
        if (!AuthUtils.isAdminOrHr(request) && !contract.getEmployeeId().equals(currentEmployeeId(request))) {
            throw new BusinessException(403, "只能访问自己的合同记录");
        }
    }
}
