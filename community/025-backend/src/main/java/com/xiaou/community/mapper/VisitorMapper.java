package com.xiaou.community.mapper;

import com.xiaou.community.entity.Visitor;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface VisitorMapper {
    void insert(Visitor visitor);
    void update(Visitor visitor);
    Visitor findById(Integer id);
    List<Visitor> findByOwnerId(Integer ownerId);
    List<Visitor> findAll();
}
