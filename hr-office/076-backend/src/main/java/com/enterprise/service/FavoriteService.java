package com.enterprise.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.enterprise.common.BusinessException;
import com.enterprise.entity.Favorite;
import com.enterprise.entity.RepairService;
import com.enterprise.mapper.FavoriteMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteService {

    @Resource
    private FavoriteMapper favoriteMapper;

    @Resource
    private RepairServiceService repairServiceService;

    public Boolean toggle(Long userId, Long serviceId) {
        if (serviceId == null) {
            throw new BusinessException("企业信息ID不能为空");
        }
        repairServiceService.mustGetById(serviceId);
        Favorite favorite = favoriteMapper.selectOne(new QueryWrapper<Favorite>()
                .eq("user_id", userId)
                .eq("service_id", serviceId));
        if (favorite != null) {
            favoriteMapper.deleteById(favorite.getId());
            return false;
        }
        Favorite entity = new Favorite();
        entity.setUserId(userId);
        entity.setServiceId(serviceId);
        favoriteMapper.insert(entity);
        return true;
    }

    public List<RepairService> myFavorites(Long userId) {
        List<Favorite> favorites = favoriteMapper.selectList(new QueryWrapper<Favorite>()
                .eq("user_id", userId)
                .orderByDesc("id"));
        List<RepairService> services = new ArrayList<>();
        for (Favorite favorite : favorites) {
            try {
                services.add(repairServiceService.mustGetById(favorite.getServiceId()));
            } catch (Exception ignored) {
            }
        }
        return services;
    }

    public Boolean check(Long userId, Long serviceId) {
        if (serviceId == null) {
            throw new BusinessException("企业信息ID不能为空");
        }
        Long count = favoriteMapper.selectCount(new QueryWrapper<Favorite>()
                .eq("user_id", userId)
                .eq("service_id", serviceId));
        return count > 0;
    }
}







