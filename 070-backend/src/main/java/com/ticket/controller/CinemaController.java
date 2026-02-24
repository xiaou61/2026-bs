package com.ticket.controller;

import com.ticket.common.Result;
import com.ticket.entity.Cinema;
import com.ticket.service.CinemaService;
import com.ticket.utils.AuthUtils;
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
@RequestMapping("/api/cinema")
public class CinemaController {

    @Resource
    private CinemaService cinemaService;

    @GetMapping("/public/list")
    public Result<?> publicList() {
        return Result.success(cinemaService.publicList());
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(cinemaService.page(pageNum, pageSize, name, status));
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        return Result.success(cinemaService.getById(id));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Cinema cinema, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        cinemaService.save(cinema);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        cinemaService.deleteById(id);
        return Result.success();
    }
}
