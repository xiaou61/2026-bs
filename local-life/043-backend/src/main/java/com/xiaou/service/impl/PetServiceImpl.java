package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.common.BusinessException;
import com.xiaou.dto.PetDTO;
import com.xiaou.entity.Pet;
import com.xiaou.mapper.PetMapper;
import com.xiaou.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements PetService {

    @Override
    public void addPet(Long userId, PetDTO dto) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(dto, pet);
        pet.setUserId(userId);
        save(pet);
    }

    @Override
    public void updatePet(Long userId, PetDTO dto) {
        Pet pet = getById(dto.getId());
        if (pet == null || !pet.getUserId().equals(userId)) {
            throw new BusinessException("宠物不存在");
        }
        BeanUtils.copyProperties(dto, pet);
        updateById(pet);
    }

    @Override
    public void deletePet(Long userId, Long id) {
        Pet pet = getById(id);
        if (pet == null || !pet.getUserId().equals(userId)) {
            throw new BusinessException("宠物不存在");
        }
        removeById(id);
    }

    @Override
    public List<Pet> listByUserId(Long userId) {
        return list(new LambdaQueryWrapper<Pet>().eq(Pet::getUserId, userId));
    }

    @Override
    public IPage<Pet> page(Long userId, Integer current, Integer size) {
        return page(new Page<>(current, size),
                new LambdaQueryWrapper<Pet>().eq(Pet::getUserId, userId).orderByDesc(Pet::getCreateTime));
    }
}
