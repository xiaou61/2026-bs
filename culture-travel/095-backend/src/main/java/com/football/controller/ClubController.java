package com.football.controller;

import com.football.common.Result;
import com.football.entity.ClubInfo;
import com.football.service.ClubService;
import com.football.utils.AuthUtils;
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
@RequestMapping("/api/club")
public class ClubController {

    @Resource
    private ClubService clubService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String clubName,
                          @RequestParam(required = false) String city,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(clubService.page(pageNum, pageSize, clubName, city, status));
    }

    @GetMapping("/all")
    public Result<?> all(HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(clubService.listAll());
    }

    @GetMapping("/public/list")
    public Result<?> publicList() {
        return Result.success(clubService.listAll());
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody ClubInfo info, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        clubService.save(info);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        clubService.deleteById(id);
        return Result.success();
    }
}
