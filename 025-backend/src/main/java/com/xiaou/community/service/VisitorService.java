package com.xiaou.community.service;

import com.xiaou.community.entity.Visitor;
import java.util.List;

public interface VisitorService {
    void register(Visitor visitor);
    void updateStatus(Integer id, String status);
    List<Visitor> getByOwnerId(Integer ownerId);
    List<Visitor> getAll();
}
