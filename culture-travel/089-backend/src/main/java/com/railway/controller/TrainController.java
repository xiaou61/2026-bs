package com.railway.controller;

import com.railway.common.Result;
import com.railway.entity.Train;
import com.railway.service.TrainService;
import com.railway.utils.AuthUtils;
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
@RequestMapping("/api/train")
public class TrainController {

    @Resource
    private TrainService trainService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String trainCode,
                          @RequestParam(required = false) String trainType,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireStaff((String) request.getAttribute("role"));
        return Result.success(trainService.page(pageNum, pageSize, trainCode, trainType, status));
    }

    @GetMapping("/enabled")
    public Result<?> enabled() {
        return Result.success(trainService.enabledList());
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Train train, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        trainService.save(train);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        trainService.deleteById(id);
        return Result.success();
    }
}
