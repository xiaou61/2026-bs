package com.ticket.controller;

import com.ticket.common.Result;
import com.ticket.entity.Hall;
import com.ticket.service.HallService;
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
@RequestMapping("/api/hall")
public class HallController {

    @Resource
    private HallService hallService;

    @GetMapping("/public/list")
    public Result<?> publicList(@RequestParam Long cinemaId) {
        return Result.success(hallService.listByCinema(cinemaId));
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long cinemaId,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(hallService.page(pageNum, pageSize, cinemaId, status));
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        return Result.success(hallService.getById(id));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Hall hall, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        hallService.save(hall);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        hallService.deleteById(id);
        return Result.success();
    }
}
