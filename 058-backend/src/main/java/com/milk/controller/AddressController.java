package com.milk.controller;

import com.milk.common.Result;
import com.milk.entity.Address;
import com.milk.service.AddressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Resource
    private AddressService addressService;

    @GetMapping("/list")
    public Result<?> list(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(addressService.listByUserId(userId));
    }

    @PostMapping
    public Result<?> add(@RequestBody Address address, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        address.setUserId(userId);
        addressService.save(address);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Address address) {
        addressService.save(address);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        addressService.deleteById(id);
        return Result.success();
    }

    @PutMapping("/default/{id}")
    public Result<?> setDefault(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        addressService.setDefault(id, userId);
        return Result.success();
    }
}
