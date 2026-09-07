package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.dto.ResearchDTO;
import com.xiaou.service.ResearchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/researcher")
@RequiredArgsConstructor
public class ResearcherController {
    private final ResearchService researchService;

    @GetMapping("/researches")
    public Result<?> list(HttpServletRequest request,
                          @RequestParam(defaultValue = "1") int current,
                          @RequestParam(defaultValue = "10") int size) {
        Long authorId = (Long) request.getAttribute("userId");
        return Result.success(researchService.pageByAuthor(authorId, current, size));
    }

    @PostMapping("/research")
    public Result<?> save(HttpServletRequest request, @RequestBody ResearchDTO dto) {
        Long authorId = (Long) request.getAttribute("userId");
        researchService.save(authorId, dto);
        return Result.success();
    }

    @PutMapping("/research")
    public Result<?> update(@RequestBody ResearchDTO dto) {
        researchService.update(dto);
        return Result.success();
    }
}
