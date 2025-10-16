package com.xiaou.campusclub.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.campusclub.common.Result;
import com.xiaou.campusclub.entity.InterestTag;
import com.xiaou.campusclub.mapper.InterestTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/interests")
public class InterestController {
    
    @Autowired
    private InterestTagMapper interestTagMapper;
    
    @GetMapping
    public Result<List<InterestTag>> getAllInterests() {
        return Result.success(interestTagMapper.selectList(null));
    }
    
    @GetMapping("/categories")
    public Result<Map<String, List<InterestTag>>> getInterestCategories() {
        List<InterestTag> tags = interestTagMapper.selectList(null);
        Map<String, List<InterestTag>> categoryMap = tags.stream()
                .collect(Collectors.groupingBy(InterestTag::getCategory));
        return Result.success(categoryMap);
    }
}

