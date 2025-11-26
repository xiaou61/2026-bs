package com.xiaou.community.service.impl;

import com.xiaou.community.entity.Repair;
import com.xiaou.community.mapper.RepairMapper;
import com.xiaou.community.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class RepairServiceImpl implements RepairService {
    @Autowired
    private RepairMapper repairMapper;

    @Override
    public void submit(Repair repair) {
        repair.setStatus("PENDING");
        repairMapper.insert(repair);
    }

    @Override
    public void updateStatus(Integer id, String status) {
        Repair repair = new Repair();
        repair.setId(id);
        repair.setStatus(status);
        if ("COMPLETED".equals(status)) {
            repair.setFinishTime(new Date());
        }
        repairMapper.update(repair);
    }

    @Override
    public List<Repair> getByOwnerId(Integer ownerId) {
        return repairMapper.findByOwnerId(ownerId);
    }

    @Override
    public List<Repair> getAll() {
        return repairMapper.findAll();
    }
}
