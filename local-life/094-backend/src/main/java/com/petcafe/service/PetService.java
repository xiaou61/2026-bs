package com.petcafe.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petcafe.common.BusinessException;
import com.petcafe.common.PageResult;
import com.petcafe.entity.CafeShop;
import com.petcafe.entity.ResidentPet;
import com.petcafe.mapper.CafeShopMapper;
import com.petcafe.mapper.ResidentPetMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PetService {

    @Resource
    private ResidentPetMapper petMapper;

    @Resource
    private CafeShopMapper shopMapper;

    public PageResult<ResidentPet> page(Integer pageNum, Integer pageSize, Long shopId, String name, String interactionStatus) {
        Page<ResidentPet> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ResidentPet> wrapper = new LambdaQueryWrapper<ResidentPet>()
                .eq(shopId != null, ResidentPet::getShopId, shopId)
                .like(StringUtils.hasText(name), ResidentPet::getName, name == null ? null : name.trim())
                .eq(StringUtils.hasText(interactionStatus), ResidentPet::getInteractionStatus, interactionStatus == null ? null : interactionStatus.trim().toUpperCase())
                .orderByDesc(ResidentPet::getId);
        Page<ResidentPet> resultPage = petMapper.selectPage(page, wrapper);
        fillShopName(resultPage.getRecords());
        PageResult<ResidentPet> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<ResidentPet> publicList() {
        List<ResidentPet> list = petMapper.selectList(new LambdaQueryWrapper<ResidentPet>()
                .eq(ResidentPet::getInteractionStatus, "OPEN")
                .orderByDesc(ResidentPet::getStarLevel)
                .orderByDesc(ResidentPet::getId));
        fillShopName(list);
        return list;
    }

    public ResidentPet getById(Long id) {
        ResidentPet pet = petMapper.selectById(id);
        if (pet == null) {
            throw new BusinessException("店宠不存在");
        }
        fillShopName(java.util.Collections.singletonList(pet));
        return pet;
    }

    public void save(ResidentPet pet) {
        if (pet == null || !StringUtils.hasText(pet.getPetNo()) || !StringUtils.hasText(pet.getName()) || pet.getShopId() == null) {
            throw new BusinessException("店宠信息不完整");
        }
        CafeShop shop = shopMapper.selectById(pet.getShopId());
        if (shop == null) {
            throw new BusinessException("门店不存在");
        }
        validatePetNo(pet);
        if (pet.getId() == null) {
            pet.setInteractionStatus(StringUtils.hasText(pet.getInteractionStatus()) ? pet.getInteractionStatus().trim().toUpperCase() : "OPEN");
            pet.setStarLevel(pet.getStarLevel() == null ? 5 : pet.getStarLevel());
            petMapper.insert(pet);
            return;
        }
        ResidentPet db = getById(pet.getId());
        db.setPetNo(pet.getPetNo().trim());
        db.setShopId(pet.getShopId());
        db.setName(pet.getName().trim());
        db.setPetType(StringUtils.hasText(pet.getPetType()) ? pet.getPetType().trim().toUpperCase() : null);
        db.setBreed(StringUtils.hasText(pet.getBreed()) ? pet.getBreed().trim() : null);
        db.setGender(StringUtils.hasText(pet.getGender()) ? pet.getGender().trim() : null);
        db.setAge(pet.getAge());
        db.setPersonality(StringUtils.hasText(pet.getPersonality()) ? pet.getPersonality().trim() : null);
        db.setHealthStatus(StringUtils.hasText(pet.getHealthStatus()) ? pet.getHealthStatus().trim() : null);
        db.setStarLevel(pet.getStarLevel() == null ? db.getStarLevel() : pet.getStarLevel());
        db.setInteractionStatus(StringUtils.hasText(pet.getInteractionStatus()) ? pet.getInteractionStatus().trim().toUpperCase() : db.getInteractionStatus());
        db.setAvatar(StringUtils.hasText(pet.getAvatar()) ? pet.getAvatar().trim() : null);
        db.setIntroduction(StringUtils.hasText(pet.getIntroduction()) ? pet.getIntroduction().trim() : null);
        petMapper.updateById(db);
    }

    public void deleteById(Long id) {
        petMapper.deleteById(id);
    }

    public Long countAll() {
        return petMapper.selectCount(null);
    }

    private void validatePetNo(ResidentPet pet) {
        ResidentPet exist = petMapper.selectOne(new LambdaQueryWrapper<ResidentPet>()
                .eq(ResidentPet::getPetNo, pet.getPetNo().trim())
                .last("limit 1"));
        if (exist != null && (pet.getId() == null || !exist.getId().equals(pet.getId()))) {
            throw new BusinessException("店宠编号已存在");
        }
    }

    private void fillShopName(List<ResidentPet> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<Long> shopIds = list.stream().map(ResidentPet::getShopId).distinct().collect(Collectors.toList());
        Map<Long, String> shopMap = shopMapper.selectList(new LambdaQueryWrapper<CafeShop>().in(CafeShop::getId, shopIds))
                .stream()
                .collect(Collectors.toMap(CafeShop::getId, CafeShop::getName, (a, b) -> a, HashMap::new));
        list.forEach(item -> item.setShopName(shopMap.get(item.getShopId())));
    }
}
