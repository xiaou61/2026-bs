package com.disaster.controller;

import com.disaster.common.Result;
import com.disaster.entity.Dispatch;
import com.disaster.entity.DispatchItem;
import com.disaster.service.DispatchService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dispatch")
public class DispatchController {

    @Autowired
    private DispatchService dispatchService;

    @PostMapping("/create")
    public Result<Void> create(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Dispatch dispatch = new Dispatch();
        dispatch.setDisasterId(params.get("disasterId") != null ? Long.valueOf(params.get("disasterId").toString()) : null);
        dispatch.setFromWarehouseId(Long.valueOf(params.get("fromWarehouseId").toString()));
        dispatch.setToAddress((String) params.get("toAddress"));
        dispatch.setPriority(Integer.valueOf(params.get("priority").toString()));
        dispatch.setRemark((String) params.get("remark"));
        List<Map<String, Object>> itemList = (List<Map<String, Object>>) params.get("items");
        List<DispatchItem> items = itemList.stream().map(m -> {
            DispatchItem item = new DispatchItem();
            item.setMaterialId(Long.valueOf(m.get("materialId").toString()));
            item.setQuantity(Integer.valueOf(m.get("quantity").toString()));
            return item;
        }).toList();
        dispatchService.create(dispatch, items, userId);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "10") int pageSize,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) Integer priority) {
        return Result.success(dispatchService.page(pageNum, pageSize, status, priority));
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        result.put("dispatch", dispatchService.getById(id));
        result.put("items", dispatchService.getItems(id));
        return Result.success(result);
    }

    @PutMapping("/{id}/approve")
    public Result<Void> approve(@PathVariable Long id, @RequestBody Map<String, Object> params, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String remark = (String) params.get("remark");
        Boolean approved = (Boolean) params.get("approved");
        dispatchService.approve(id, userId, remark, approved);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        dispatchService.updateStatus(id, params.get("status"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        dispatchService.delete(id);
        return Result.success();
    }
}
