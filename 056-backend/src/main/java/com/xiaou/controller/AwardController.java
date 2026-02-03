package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/award")
public class AwardController {

    @Autowired
    private AwardService awardService;

    @GetMapping("/list/{competitionId}")
    public Result<?> list(@PathVariable Long competitionId) {
        return Result.success(awardService.getByCompetitionId(competitionId));
    }

    @GetMapping("/my")
    public Result<?> myAwards(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(awardService.getMyAwards(userId));
    }

    @PostMapping("/generate/{competitionId}")
    public Result<?> generate(@PathVariable Long competitionId, @RequestBody Map<String, Object> params) {
        List<Map<String, Object>> awards = (List<Map<String, Object>>) params.get("awards");
        awardService.generate(competitionId, awards);
        return Result.success();
    }
}
