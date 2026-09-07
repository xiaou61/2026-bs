package com.rental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rental.common.BusinessException;
import com.rental.entity.House;
import com.rental.entity.HouseFavorite;
import com.rental.entity.Review;
import com.rental.entity.User;
import com.rental.mapper.HouseFavoriteMapper;
import com.rental.mapper.HouseMapper;
import com.rental.mapper.ReviewMapper;
import com.rental.mapper.UserMapper;
import com.rental.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private HouseFavoriteMapper houseFavoriteMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public void publish(Long landlordId, House house) {
        house.setLandlordId(landlordId);
        house.setStatus(0); // 待审核
        house.setViewCount(0);
        houseMapper.insert(house);
    }

    @Override
    public void update(Long landlordId, House house) {
        House existing = houseMapper.selectById(house.getId());
        if (existing == null) {
            throw new BusinessException("房源不存在");
        }
        if (!existing.getLandlordId().equals(landlordId)) {
            throw new BusinessException("无权操作此房源");
        }

        // 更新后需要重新审核
        house.setStatus(0);
        houseMapper.updateById(house);
    }

    @Override
    public void delete(Long landlordId, Long houseId) {
        House house = houseMapper.selectById(houseId);
        if (house == null) {
            throw new BusinessException("房源不存在");
        }
        if (!house.getLandlordId().equals(landlordId)) {
            throw new BusinessException("无权操作此房源");
        }
        houseMapper.deleteById(houseId);
    }

    @Override
    public IPage<Map<String, Object>> getList(int page, int size, String city, String district,
            BigDecimal minPrice, BigDecimal maxPrice, String roomType, String keyword) {
        
        LambdaQueryWrapper<House> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(House::getStatus, 1); // 只显示已上架
        
        if (StringUtils.hasText(city)) {
            wrapper.eq(House::getCity, city);
        }
        if (StringUtils.hasText(district)) {
            wrapper.eq(House::getDistrict, district);
        }
        if (minPrice != null) {
            wrapper.ge(House::getPrice, minPrice);
        }
        if (maxPrice != null) {
            wrapper.le(House::getPrice, maxPrice);
        }
        if (StringUtils.hasText(roomType)) {
            wrapper.eq(House::getRoomType, roomType);
        }
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(House::getTitle, keyword)
                    .or().like(House::getAddress, keyword));
        }
        
        wrapper.orderByDesc(House::getCreateTime);
        
        IPage<House> housePage = houseMapper.selectPage(new Page<>(page, size), wrapper);
        
        // 转换为VO
        return housePage.convert(this::convertToListVO);
    }

    @Override
    public Map<String, Object> getDetail(Long houseId, Long userId) {
        House house = houseMapper.selectById(houseId);
        if (house == null) {
            throw new BusinessException("房源不存在");
        }

        // 增加浏览次数
        house.setViewCount(house.getViewCount() + 1);
        houseMapper.updateById(house);

        Map<String, Object> vo = convertToDetailVO(house);

        // 获取房东信息
        User landlord = userMapper.selectById(house.getLandlordId());
        if (landlord != null) {
            Map<String, Object> landlordVO = new HashMap<>();
            landlordVO.put("id", landlord.getId());
            landlordVO.put("realName", landlord.getRealName());
            landlordVO.put("phone", landlord.getPhone());
            landlordVO.put("avatar", landlord.getAvatar());
            vo.put("landlord", landlordVO);
        }

        // 检查是否收藏
        if (userId != null) {
            vo.put("isFavorite", isFavorite(userId, houseId));
        }

        // 获取评价统计
        vo.put("reviewStats", getReviewStats(houseId));

        return vo;
    }

    @Override
    public IPage<House> getMyHouses(Long landlordId, int page, int size, Integer status) {
        LambdaQueryWrapper<House> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(House::getLandlordId, landlordId);
        if (status != null) {
            wrapper.eq(House::getStatus, status);
        }
        wrapper.orderByDesc(House::getCreateTime);
        return houseMapper.selectPage(new Page<>(page, size), wrapper);
    }

    @Override
    public void updateStatus(Long landlordId, Long houseId, Integer status) {
        House house = houseMapper.selectById(houseId);
        if (house == null) {
            throw new BusinessException("房源不存在");
        }
        if (!house.getLandlordId().equals(landlordId)) {
            throw new BusinessException("无权操作此房源");
        }
        
        // 只允许在已上架和已下架之间切换
        if (status != 1 && status != 2) {
            throw new BusinessException("无效的状态");
        }
        
        house.setStatus(status);
        houseMapper.updateById(house);
    }

    @Override
    public void toggleFavorite(Long userId, Long houseId) {
        LambdaQueryWrapper<HouseFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HouseFavorite::getUserId, userId);
        wrapper.eq(HouseFavorite::getHouseId, houseId);
        
        HouseFavorite favorite = houseFavoriteMapper.selectOne(wrapper);
        if (favorite != null) {
            // 取消收藏
            houseFavoriteMapper.deleteById(favorite.getId());
        } else {
            // 添加收藏
            favorite = new HouseFavorite();
            favorite.setUserId(userId);
            favorite.setHouseId(houseId);
            houseFavoriteMapper.insert(favorite);
        }
    }

    @Override
    public IPage<Map<String, Object>> getFavorites(Long userId, int page, int size) {
        // 获取收藏的房源ID列表
        LambdaQueryWrapper<HouseFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HouseFavorite::getUserId, userId);
        wrapper.orderByDesc(HouseFavorite::getCreateTime);
        
        IPage<HouseFavorite> favoritePage = houseFavoriteMapper.selectPage(new Page<>(page, size), wrapper);
        
        return favoritePage.convert(fav -> {
            House house = houseMapper.selectById(fav.getHouseId());
            if (house != null) {
                Map<String, Object> vo = convertToListVO(house);
                vo.put("favoriteTime", fav.getCreateTime());
                return vo;
            }
            return null;
        });
    }

    @Override
    public boolean isFavorite(Long userId, Long houseId) {
        LambdaQueryWrapper<HouseFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HouseFavorite::getUserId, userId);
        wrapper.eq(HouseFavorite::getHouseId, houseId);
        return houseFavoriteMapper.selectCount(wrapper) > 0;
    }

    private Map<String, Object> convertToListVO(House house) {
        Map<String, Object> vo = new HashMap<>();
        vo.put("id", house.getId());
        vo.put("title", house.getTitle());
        vo.put("city", house.getCity());
        vo.put("district", house.getDistrict());
        vo.put("address", house.getAddress());
        vo.put("price", house.getPrice());
        vo.put("area", house.getArea());
        vo.put("roomType", house.getRoomType());
        vo.put("floor", house.getFloor());
        vo.put("totalFloor", house.getTotalFloor());
        vo.put("orientation", house.getOrientation());
        vo.put("decoration", house.getDecoration());
        vo.put("images", house.getImages());
        vo.put("status", house.getStatus());
        vo.put("viewCount", house.getViewCount());
        vo.put("createTime", house.getCreateTime());
        return vo;
    }

    private Map<String, Object> convertToDetailVO(House house) {
        Map<String, Object> vo = convertToListVO(house);
        vo.put("landlordId", house.getLandlordId());
        vo.put("description", house.getDescription());
        vo.put("province", house.getProvince());
        vo.put("deposit", house.getDeposit());
        vo.put("facilities", house.getFacilities());
        return vo;
    }

    private Map<String, Object> getReviewStats(Long houseId) {
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getHouseId, houseId);
        List<Review> reviews = reviewMapper.selectList(wrapper);

        Map<String, Object> stats = new HashMap<>();
        if (reviews.isEmpty()) {
            stats.put("count", 0);
            stats.put("avgScore", 0);
        } else {
            double avgHouse = reviews.stream().mapToInt(Review::getHouseScore).average().orElse(0);
            double avgService = reviews.stream().mapToInt(Review::getServiceScore).average().orElse(0);
            double avgFacility = reviews.stream().mapToInt(Review::getFacilityScore).average().orElse(0);
            double avgTotal = (avgHouse + avgService + avgFacility) / 3;
            
            stats.put("count", reviews.size());
            stats.put("avgScore", Math.round(avgTotal * 10) / 10.0);
            stats.put("avgHouseScore", Math.round(avgHouse * 10) / 10.0);
            stats.put("avgServiceScore", Math.round(avgService * 10) / 10.0);
            stats.put("avgFacilityScore", Math.round(avgFacility * 10) / 10.0);
        }
        return stats;
    }
}
