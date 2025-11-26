package com.xiaou.pet.controller;

import com.xiaou.pet.common.Result;
import com.xiaou.pet.entity.Pet;
import com.xiaou.pet.service.PetService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Resource
    private PetService petService;

    @PostMapping("/add")
    public Result<Pet> addPet(@RequestBody Pet pet) {
        return petService.addPet(pet);
    }

    @PutMapping("/update")
    public Result<Pet> updatePet(@RequestBody Pet pet) {
        return petService.updatePet(pet);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> deletePet(@PathVariable Long id) {
        return petService.deletePet(id);
    }

    @GetMapping("/{id}")
    public Result<Pet> getPetById(@PathVariable Long id) {
        return petService.getPetById(id);
    }

    @GetMapping("/list")
    public Result<List<Pet>> getPetList(@RequestParam(required = false) String type,
                                        @RequestParam(required = false) String status) {
        return petService.getPetList(type, status);
    }
}
