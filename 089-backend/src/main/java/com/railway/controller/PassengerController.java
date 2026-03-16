package com.railway.controller;

import com.railway.common.Result;
import com.railway.entity.PassengerProfile;
import com.railway.service.PassengerService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController {

    @Resource
    private PassengerService passengerService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String passengerName,
                          HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(passengerService.pageByUser(userId, pageNum, pageSize, passengerName));
    }

    @GetMapping("/all")
    public Result<?> all(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(passengerService.listByUser(userId));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody PassengerProfile profile, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        passengerService.save(userId, profile);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        passengerService.delete(userId, id);
        return Result.success();
    }
}
