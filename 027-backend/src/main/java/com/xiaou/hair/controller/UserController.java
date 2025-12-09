package com.xiaou.hair.controller;

import com.xiaou.hair.common.Result;
import com.xiaou.hair.service.UserService;
import com.xiaou.hair.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取积分信息
     */
    @GetMapping("/points")
    public Result<UserInfoVO> getPoints() {
        UserInfoVO userInfo = userService.getCurrentUserInfo();
        return Result.success(userInfo);
    }

    /**
     * 获取余额信息
     */
    @GetMapping("/balance")
    public Result<UserInfoVO> getBalance() {
        UserInfoVO userInfo = userService.getCurrentUserInfo();
        return Result.success(userInfo);
    }

    /**
     * 余额充值
     */
    @PostMapping("/balance/recharge")
    public Result<Void> rechargeBalance(@RequestParam BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return Result.error("充值金额必须大于0");
        }
        
        userService.rechargeBalance(amount);
        return Result.success("充值成功");
    }
}
