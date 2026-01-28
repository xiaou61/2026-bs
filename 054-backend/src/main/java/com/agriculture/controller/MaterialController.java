package com.agriculture.controller;

import com.agriculture.common.Result;
import com.agriculture.entity.AgriculturalMaterial;
import com.agriculture.entity.MaterialRecord;
import com.agriculture.service.MaterialService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/page")
    public Result<Page<AgriculturalMaterial>> getPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                                       @RequestParam(required = false) String name,
                                                       @RequestParam(required = false) String category) {
        return Result.success(materialService.getPage(pageNum, pageSize, name, category));
    }

    @PostMapping
    public Result<?> add(@RequestBody AgriculturalMaterial material) {
        materialService.save(material);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody AgriculturalMaterial material) {
        materialService.updateById(material);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        materialService.removeById(id);
        return Result.success();
    }

    @GetMapping("/record/page")
    public Result<Page<MaterialRecord>> getRecordPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                                       @RequestParam(required = false) Long materialId) {
        return Result.success(materialService.getRecordPage(pageNum, pageSize, materialId));
    }

    @PostMapping("/in")
    public Result<?> stockIn(@RequestBody Map<String, Object> body, HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        Long materialId = Long.parseLong(body.get("materialId").toString());
        Integer quantity = Integer.parseInt(body.get("quantity").toString());
        BigDecimal unitPrice = new BigDecimal(body.get("unitPrice").toString());
        String purpose = (String) body.get("purpose");
        materialService.stockIn(materialId, quantity, unitPrice, purpose, userId);
        return Result.success();
    }

    @PostMapping("/out")
    public Result<?> stockOut(@RequestBody Map<String, Object> body, HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        Long materialId = Long.parseLong(body.get("materialId").toString());
        Integer quantity = Integer.parseInt(body.get("quantity").toString());
        String purpose = (String) body.get("purpose");
        materialService.stockOut(materialId, quantity, purpose, userId);
        return Result.success();
    }

    @GetMapping("/warning")
    public Result<List<AgriculturalMaterial>> getWarning() {
        return Result.success(materialService.getWarningList());
    }
}
