package com.petcafe.controller;

import com.petcafe.common.Result;
import com.petcafe.entity.ResidentPet;
import com.petcafe.service.PetService;
import com.petcafe.utils.AuthUtils;
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
@RequestMapping("/api/pet")
public class PetController {

    @Resource
    private PetService petService;

    @GetMapping("/public/list")
    public Result<?> publicList() {
        return Result.success(petService.publicList());
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long shopId,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) String interactionStatus,
                          HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(petService.page(pageNum, pageSize, shopId, name, interactionStatus));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody ResidentPet pet, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        petService.save(pet);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        petService.deleteById(id);
        return Result.success();
    }
}
