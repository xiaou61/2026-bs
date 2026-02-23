package com.classic.controller;

import com.classic.common.BusinessException;
import com.classic.common.Result;
import com.classic.service.FavoriteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    @Resource
    private FavoriteService favoriteService;

    @GetMapping("/my-page")
    public Result<?> myPage(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(required = false) String targetType,
                            HttpServletRequest request) {
        checkUser((String) request.getAttribute("role"));
        return Result.success(favoriteService.myPage((Long) request.getAttribute("userId"), pageNum, pageSize, targetType));
    }

    @PostMapping("/toggle")
    public Result<?> toggle(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkUser((String) request.getAttribute("role"));
        if (params.get("targetType") == null || params.get("targetId") == null) {
            throw new BusinessException("参数不完整");
        }
        favoriteService.toggle((Long) request.getAttribute("userId"), params.get("targetType").toString(), ((Number) params.get("targetId")).longValue());
        return Result.success();
    }

    private void checkUser(String role) {
        if (!"USER".equals(role)) {
            throw new BusinessException("仅用户可操作收藏");
        }
    }
}
