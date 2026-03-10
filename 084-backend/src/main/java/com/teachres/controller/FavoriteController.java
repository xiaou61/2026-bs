package com.teachres.controller;

import com.teachres.common.Result;
import com.teachres.entity.StudyList;
import com.teachres.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/list")
    public Result<List<Map<String, Object>>> list(@RequestAttribute("userId") Long userId) {
        return Result.success(favoriteService.list(userId));
    }

    @PostMapping("/add/{materialId}")
    public Result<String> add(@PathVariable Long materialId,
                              @RequestAttribute("userId") Long userId) {
        favoriteService.add(userId, materialId);
        return Result.success();
    }

    @DeleteMapping("/cancel/{materialId}")
    public Result<String> cancel(@PathVariable Long materialId,
                                 @RequestAttribute("userId") Long userId) {
        favoriteService.cancel(userId, materialId);
        return Result.success();
    }

    @GetMapping("/study/list")
    public Result<List<StudyList>> studyList(@RequestAttribute("userId") Long userId) {
        return Result.success(favoriteService.studyList(userId));
    }

    @PutMapping("/study/update")
    public Result<String> updateStudy(@RequestBody StudyList studyList,
                                      @RequestAttribute("userId") Long userId) {
        favoriteService.updateStudy(studyList, userId);
        return Result.success();
    }

    @DeleteMapping("/study/delete/{id}")
    public Result<String> deleteStudy(@PathVariable Long id) {
        favoriteService.deleteStudy(id);
        return Result.success();
    }
}
