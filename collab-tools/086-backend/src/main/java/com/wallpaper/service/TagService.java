package com.wallpaper.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wallpaper.common.BusinessException;
import com.wallpaper.entity.WallpaperTag;
import com.wallpaper.mapper.WallpaperTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private WallpaperTagMapper tagMapper;

    @Autowired
    private AuthService authService;

    public IPage<WallpaperTag> list(String name, Integer status, Integer pageNum, Integer pageSize) {
        Page<WallpaperTag> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<WallpaperTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(name), WallpaperTag::getName, name)
                .eq(status != null, WallpaperTag::getStatus, status)
                .orderByAsc(WallpaperTag::getSort)
                .orderByDesc(WallpaperTag::getId);
        return tagMapper.selectPage(page, wrapper);
    }

    public List<WallpaperTag> enabledList() {
        LambdaQueryWrapper<WallpaperTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WallpaperTag::getStatus, 1)
                .orderByAsc(WallpaperTag::getSort)
                .orderByDesc(WallpaperTag::getId);
        return tagMapper.selectList(wrapper);
    }

    public void add(WallpaperTag tag, Long userId) {
        authService.assertAdmin(userId);
        validateTag(tag);
        if (tag.getSort() == null) {
            tag.setSort(0);
        }
        if (tag.getStatus() == null) {
            tag.setStatus(1);
        }
        tagMapper.insert(tag);
    }

    public void update(WallpaperTag tag, Long userId) {
        authService.assertAdmin(userId);
        if (tag.getId() == null) {
            throw new BusinessException("标签ID不能为空");
        }
        validateTag(tag);
        tagMapper.updateById(tag);
    }

    public void delete(Long id, Long userId) {
        authService.assertAdmin(userId);
        tagMapper.deleteById(id);
    }

    private void validateTag(WallpaperTag tag) {
        if (tag == null || !StringUtils.hasText(tag.getName())) {
            throw new BusinessException("标签名称不能为空");
        }
    }
}
