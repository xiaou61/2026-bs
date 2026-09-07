package com.xiaou.community.mapper;

import com.xiaou.community.entity.Owner;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface OwnerMapper {
    void insert(Owner owner);
    Owner findById(Integer id);
    Owner findByUserId(Integer userId);
    void update(Owner owner);
    void deleteById(Integer id);
    List<Owner> findAll();
}
