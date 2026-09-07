package com.xiaou.community.mapper;

import com.xiaou.community.entity.Repair;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface RepairMapper {
    void insert(Repair repair);
    void update(Repair repair);
    Repair findById(Integer id);
    List<Repair> findByOwnerId(Integer ownerId);
    List<Repair> findAll();
}
