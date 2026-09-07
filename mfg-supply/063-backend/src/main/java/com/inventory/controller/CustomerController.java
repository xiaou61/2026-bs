package com.inventory.controller;

import com.inventory.common.BusinessException;
import com.inventory.common.Result;
import com.inventory.entity.Customer;
import com.inventory.service.CustomerService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(customerService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) String contactPerson,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        return Result.success(customerService.page(pageNum, pageSize, name, contactPerson, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody Customer customer, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        customerService.save(customer);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Customer customer, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        customerService.save(customer);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        customerService.deleteById(id);
        return Result.success();
    }

    private void checkStaffRole(String role) {
        if (!"ADMIN".equals(role) && !"STAFF".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
