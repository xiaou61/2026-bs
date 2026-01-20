package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.dto.PetDTO;
import com.xiaou.entity.Pet;
import com.xiaou.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "宠物管理")
@RestController
@RequestMapping("/api/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @Operation(summary = "添加宠物")
    @PostMapping
    public Result<Void> add(HttpServletRequest request, @Valid @RequestBody PetDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        petService.addPet(userId, dto);
        return Result.success();
    }

    @Operation(summary = "更新宠物")
    @PutMapping
    public Result<Void> update(HttpServletRequest request, @Valid @RequestBody PetDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        petService.updatePet(userId, dto);
        return Result.success();
    }

    @Operation(summary = "删除宠物")
    @DeleteMapping("/{id}")
    public Result<Void> delete(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        petService.deletePet(userId, id);
        return Result.success();
    }

    @Operation(summary = "获取宠物列表")
    @GetMapping("/list")
    public Result<List<Pet>> list(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(petService.listByUserId(userId));
    }

    @Operation(summary = "分页获取宠物")
    @GetMapping("/page")
    public Result<IPage<Pet>> page(HttpServletRequest request,
                                   @RequestParam(defaultValue = "1") Integer current,
                                   @RequestParam(defaultValue = "10") Integer size) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(petService.page(userId, current, size));
    }

    @Operation(summary = "获取宠物详情")
    @GetMapping("/{id}")
    public Result<Pet> detail(@PathVariable Long id) {
        return Result.success(petService.getById(id));
    }
}
