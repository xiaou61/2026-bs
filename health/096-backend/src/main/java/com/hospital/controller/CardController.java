package com.hospital.controller;

import com.hospital.common.Result;
import com.hospital.entity.MedicalCard;
import com.hospital.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/my")
    public Result<List<MedicalCard>> myCards(@RequestAttribute("userId") Long userId) {
        return Result.success(cardService.myCards(userId));
    }

    @PostMapping
    public Result<String> add(@RequestBody MedicalCard entity,
                              @RequestAttribute("userId") Long userId,
                              @RequestAttribute("role") String role) {
        cardService.save(entity, userId, role);
        return Result.success();
    }

    @PutMapping
    public Result<String> update(@RequestBody MedicalCard entity,
                                 @RequestAttribute("userId") Long userId,
                                 @RequestAttribute("role") String role) {
        cardService.save(entity, userId, role);
        return Result.success();
    }

    @PutMapping("/default/{id}")
    public Result<String> setDefault(@PathVariable Long id,
                                     @RequestAttribute("userId") Long userId,
                                     @RequestAttribute("role") String role) {
        cardService.setDefault(id, userId, role);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id,
                                 @RequestAttribute("userId") Long userId,
                                 @RequestAttribute("role") String role) {
        cardService.delete(id, userId, role);
        return Result.success();
    }
}
