package com.groupbuy.controller;

import com.groupbuy.common.Result;
import com.groupbuy.entity.Address;
import com.groupbuy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/list")
    public Result<?> list(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(addressService.list(userId));
    }

    @PostMapping("/add")
    public Result<?> add(HttpServletRequest request, @RequestBody Address address) {
        Long userId = (Long) request.getAttribute("userId");
        addressService.add(userId, address);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody Address address) {
        addressService.update(address);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        addressService.delete(id);
        return Result.success();
    }

    @PutMapping("/default/{id}")
    public Result<?> setDefault(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        addressService.setDefault(userId, id);
        return Result.success();
    }
}
