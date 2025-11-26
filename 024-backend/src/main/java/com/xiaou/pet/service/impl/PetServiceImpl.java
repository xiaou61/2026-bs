package com.xiaou.pet.service.impl;

import com.xiaou.pet.common.Result;
import com.xiaou.pet.entity.Pet;
import com.xiaou.pet.mapper.PetMapper;
import com.xiaou.pet.service.PetService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    @Resource
    private PetMapper petMapper;

    @Override
    public Result<Pet> addPet(Pet pet) {
        petMapper.insert(pet);
        return Result.success(pet);
    }

    @Override
    public Result<Pet> updatePet(Pet pet) {
        petMapper.update(pet);
        return Result.success(pet);
    }

    @Override
    public Result<Void> deletePet(Long id) {
        petMapper.deleteById(id);
        return Result.success();
    }

    @Override
    public Result<Pet> getPetById(Long id) {
        Pet pet = petMapper.selectById(id);
        return Result.success(pet);
    }

    @Override
    public Result<List<Pet>> getPetList(String type, String status) {
        List<Pet> list = petMapper.selectAll(type, status);
        return Result.success(list);
    }
}
