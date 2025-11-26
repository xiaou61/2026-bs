package com.xiaou.community.service;

import com.xiaou.community.entity.Repair;
import java.util.List;

public interface RepairService {
    void submit(Repair repair);
    void updateStatus(Integer id, String status);
    List<Repair> getByOwnerId(Integer ownerId);
    List<Repair> getAll();
}
