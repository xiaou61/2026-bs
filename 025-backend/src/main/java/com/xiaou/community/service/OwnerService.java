package com.xiaou.community.service;

import com.xiaou.community.entity.Owner;
import java.util.List;

public interface OwnerService {
    void add(Owner owner);
    void update(Owner owner);
    void delete(Integer id);
    Owner getById(Integer id);
    Owner getByUserId(Integer userId);
    List<Owner> getAll();
}
