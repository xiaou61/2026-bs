package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.dto.PetDTO;
import com.xiaou.entity.Pet;

import java.util.List;

public interface PetService extends IService<Pet> {
    void addPet(Long userId, PetDTO dto);
    void updatePet(Long userId, PetDTO dto);
    void deletePet(Long userId, Long id);
    List<Pet> listByUserId(Long userId);
    IPage<Pet> page(Long userId, Integer current, Integer size);
}
