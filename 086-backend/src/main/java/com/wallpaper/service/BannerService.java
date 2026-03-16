package com.wallpaper.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wallpaper.common.BusinessException;
import com.wallpaper.entity.CarouselBanner;
import com.wallpaper.mapper.CarouselBannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BannerService {

    @Autowired
    private CarouselBannerMapper bannerMapper;

    @Autowired
    private AuthService authService;

    public IPage<CarouselBanner> list(String title, Integer status, Integer pageNum, Integer pageSize, Long userId) {
        authService.assertAdmin(userId);
        Page<CarouselBanner> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CarouselBanner> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(title), CarouselBanner::getTitle, title)
                .eq(status != null, CarouselBanner::getStatus, status)
                .orderByAsc(CarouselBanner::getSort)
                .orderByDesc(CarouselBanner::getId);
        return bannerMapper.selectPage(page, wrapper);
    }

    public List<CarouselBanner> enabledList() {
        LambdaQueryWrapper<CarouselBanner> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CarouselBanner::getStatus, 1)
                .orderByAsc(CarouselBanner::getSort)
                .orderByDesc(CarouselBanner::getId);
        return bannerMapper.selectList(wrapper);
    }

    public void add(CarouselBanner banner, Long userId) {
        authService.assertAdmin(userId);
        validate(banner);
        if (banner.getSort() == null) {
            banner.setSort(0);
        }
        if (banner.getStatus() == null) {
            banner.setStatus(1);
        }
        bannerMapper.insert(banner);
    }

    public void update(CarouselBanner banner, Long userId) {
        authService.assertAdmin(userId);
        if (banner.getId() == null) {
            throw new BusinessException("轮播ID不能为空");
        }
        validate(banner);
        bannerMapper.updateById(banner);
    }

    public void delete(Long id, Long userId) {
        authService.assertAdmin(userId);
        bannerMapper.deleteById(id);
    }

    private void validate(CarouselBanner banner) {
        if (banner == null || !StringUtils.hasText(banner.getTitle()) || !StringUtils.hasText(banner.getImageUrl())) {
            throw new BusinessException("轮播标题和图片不能为空");
        }
    }
}
