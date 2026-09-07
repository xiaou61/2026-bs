package com.xiaou.pet.service;

import com.xiaou.pet.common.Result;
import com.xiaou.pet.entity.Pet;
import java.util.List;

public interface PetService {
    Result<Pet> addPet(Pet pet);
    Result<Pet> updatePet(Pet pet);
    Result<Void> deletePet(Long id);
    Result<Pet> getPetById(Long id);
    Result<List<Pet>> getPetList(String type, String status);
}
