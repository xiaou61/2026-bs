package com.teacher.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teacher.common.BusinessException;
import com.teacher.common.PageResult;
import com.teacher.entity.ScenicSpot;
import com.teacher.entity.UserFavorite;
import com.teacher.mapper.UserFavoriteMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    @Resource
    private UserFavoriteMapper userFavoriteMapper;

    @Resource
    private SpotService spotService;

    public PageResult<Map<String, Object>> page(Long userId, Integer pageNum, Integer pageSize, String name, String city) {
        Page<UserFavorite> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<UserFavorite> wrapper = new LambdaQueryWrapper<UserFavorite>()
                .eq(UserFavorite::getUserId, userId)
                .orderByDesc(UserFavorite::getId);
        Map<Long, ScenicSpot> preloadedSpotMap = null;
        if (StringUtils.hasText(name) || StringUtils.hasText(city)) {
            List<ScenicSpot> spotList = spotService.list(name, city);
            if (spotList.isEmpty()) {
                PageResult<Map<String, Object>> empty = new PageResult<>();
                empty.setTotal(0L);
                empty.setRecords(new ArrayList<>());
                return empty;
            }
            Set<Long> filteredSpotIds = spotList.stream().map(ScenicSpot::getId).collect(Collectors.toSet());
            wrapper.in(UserFavorite::getSpotId, filteredSpotIds);
            preloadedSpotMap = spotList.stream().collect(Collectors.toMap(ScenicSpot::getId, e -> e));
        }
        Page<UserFavorite> resultPage = userFavoriteMapper.selectPage(page, wrapper);
        List<UserFavorite> favorites = resultPage.getRecords();
        List<Map<String, Object>> records = new ArrayList<>();
        if (!favorites.isEmpty()) {
            Map<Long, ScenicSpot> spotMap = preloadedSpotMap;
            if (spotMap == null) {
                Set<Long> spotIds = favorites.stream().map(UserFavorite::getSpotId).collect(Collectors.toSet());
                spotMap = spotService.mapByIds(spotIds);
            }
            for (UserFavorite favorite : favorites) {
                ScenicSpot spot = spotMap.get(favorite.getSpotId());
                if (spot == null || spot.getStatus() == null || spot.getStatus() != 1) {
                    continue;
                }
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("favoriteId", favorite.getId());
                item.put("spotId", spot.getId());
                item.put("spotName", spot.getName());
                item.put("city", spot.getCity());
                item.put("tags", spot.getTags());
                item.put("price", spot.getPrice());
                item.put("cover", spot.getCover());
                item.put("intro", spot.getIntro());
                item.put("createTime", favorite.getCreateTime());
                records.add(item);
            }
        }
        PageResult<Map<String, Object>> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(records);
        return result;
    }

    public Map<String, Object> toggle(Long userId, Long spotId) {
        if (spotId == null) {
            throw new BusinessException("景点不能为空");
        }
        ScenicSpot spot = spotService.getEnabledById(spotId);
        if (spot == null) {
            throw new BusinessException("景点不存在或已下架");
        }
        UserFavorite existing = userFavoriteMapper.selectOne(new LambdaQueryWrapper<UserFavorite>()
                .eq(UserFavorite::getUserId, userId)
                .eq(UserFavorite::getSpotId, spotId)
                .last("limit 1"));
        Map<String, Object> result = new LinkedHashMap<>();
        if (existing != null) {
            userFavoriteMapper.deleteById(existing.getId());
            result.put("favored", false);
            return result;
        }
        UserFavorite favorite = new UserFavorite();
        favorite.setUserId(userId);
        favorite.setSpotId(spotId);
        userFavoriteMapper.insert(favorite);
        result.put("favored", true);
        return result;
    }

    public Long countByUser(Long userId) {
        return userFavoriteMapper.selectCount(new LambdaQueryWrapper<UserFavorite>().eq(UserFavorite::getUserId, userId));
    }
}

