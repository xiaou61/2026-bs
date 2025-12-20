package com.xiaou.rice.modules.machine.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.rice.common.api.Result;
import com.xiaou.rice.common.api.ResultCode;
import com.xiaou.rice.common.exception.BusinessException;
import com.xiaou.rice.modules.machine.entity.Machine;
import com.xiaou.rice.modules.machine.service.MachineService;
import com.xiaou.rice.security.SecurityUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/machines")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping
    public Result<List<Machine>> listMyMachines() {
        Long uid = SecurityUtil.currentUserId();
        List<Machine> list = machineService.list(new LambdaQueryWrapper<Machine>()
                .eq(Machine::getOwnerId, uid)
                .orderByDesc(Machine::getCreatedAt));
        return Result.ok(list);
    }

    @PostMapping
    public Result<Machine> create(@Valid @RequestBody MachineRequest req) {
        Long uid = SecurityUtil.currentUserId();
        Machine machine = mapToEntity(req);
        machine.setOwnerId(uid);
        machineService.save(machine);
        return Result.ok(machine);
    }

    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @Valid @RequestBody MachineRequest req) {
        Long uid = SecurityUtil.currentUserId();
        Machine machine = machineService.getById(id);
        if (machine == null || !machine.getOwnerId().equals(uid)) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "设备不存在或无权限");
        }
        Machine updated = mapToEntity(req);
        updated.setId(id);
        updated.setOwnerId(uid);
        return Result.ok(machineService.updateById(updated));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        Long uid = SecurityUtil.currentUserId();
        Machine machine = machineService.getById(id);
        if (machine == null || !machine.getOwnerId().equals(uid)) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "设备不存在或无权限");
        }
        return Result.ok(machineService.removeById(id));
    }

    private Machine mapToEntity(MachineRequest req) {
        Machine m = new Machine();
        m.setName(req.name());
        m.setModel(req.model());
        m.setWidth(req.width());
        m.setPower(req.power());
        m.setPricePerHour(req.pricePerHour());
        m.setPricePerMu(req.pricePerMu());
        m.setServiceRadiusKm(req.serviceRadiusKm());
        m.setStatus(req.status());
        m.setRemark(req.remark());
        return m;
    }

    public record MachineRequest(
            @NotBlank String name,
            String model,
            BigDecimal width,
            BigDecimal power,
            BigDecimal pricePerHour,
            BigDecimal pricePerMu,
            Integer serviceRadiusKm,
            @NotNull Integer status,
            String remark
    ) {}
}
