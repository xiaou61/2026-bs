package com.xiaou.pet.mapper;

import com.xiaou.pet.entity.Pet;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface PetMapper {
    int insert(Pet pet);
    int update(Pet pet);
    int deleteById(Long id);
    Pet selectById(Long id);
    List<Pet> selectAll(@Param("type") String type, @Param("status") String status);
}
