package com.xiaou.community.service;

import com.xiaou.community.entity.Fee;
import java.util.List;

public interface FeeService {
    void create(Fee fee);
    void pay(Integer id);
    List<Fee> getByOwnerId(Integer ownerId);
    List<Fee> getAll();
}
