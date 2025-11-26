package com.xiaou.community.mapper;

import com.xiaou.community.entity.Fee;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface FeeMapper {
    void insert(Fee fee);
    void update(Fee fee);
    Fee findById(Integer id);
    List<Fee> findByOwnerId(Integer ownerId);
    List<Fee> findAll();
}
