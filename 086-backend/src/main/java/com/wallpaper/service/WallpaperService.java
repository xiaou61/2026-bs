package com.wallpaper.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wallpaper.common.BusinessException;
import com.wallpaper.entity.SysUser;
import com.wallpaper.entity.WallpaperCategory;
import com.wallpaper.entity.WallpaperFavorite;
import com.wallpaper.entity.WallpaperInfo;
import com.wallpaper.entity.WallpaperTag;
import com.wallpaper.entity.WallpaperTagRel;
import com.wallpaper.entity.WallpaperDownload;
import com.wallpaper.mapper.WallpaperCategoryMapper;
import com.wallpaper.mapper.WallpaperFavoriteMapper;
import com.wallpaper.mapper.WallpaperInfoMapper;
import com.wallpaper.mapper.WallpaperTagMapper;
import com.wallpaper.mapper.WallpaperTagRelMapper;
import com.wallpaper.mapper.WallpaperDownloadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WallpaperService {

    @Autowired
    private WallpaperInfoMapper wallpaperInfoMapper;

    @Autowired
    private WallpaperCategoryMapper categoryMapper;

    @Autowired
    private WallpaperTagMapper tagMapper;

    @Autowired
    private WallpaperTagRelMapper tagRelMapper;

    @Autowired
    private WallpaperFavoriteMapper favoriteMapper;

    @Autowired
    private WallpaperDownloadMapper downloadMapper;

    @Autowired
    private AuthService authService;

    public IPage<WallpaperInfo> list(String title, Long categoryId, Integer auditStatus, Integer publishStatus,
                                     Long uploaderId, String wallpaperType, Integer pageNum, Integer pageSize) {
        Page<WallpaperInfo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<WallpaperInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(title), WallpaperInfo::getTitle, title)
                .eq(categoryId != null, WallpaperInfo::getCategoryId, categoryId)
                .eq(auditStatus != null, WallpaperInfo::getAuditStatus, auditStatus)
                .eq(publishStatus != null, WallpaperInfo::getPublishStatus, publishStatus)
                .eq(uploaderId != null, WallpaperInfo::getUploaderId, uploaderId)
                .eq(StringUtils.hasText(wallpaperType), WallpaperInfo::getWallpaperType, wallpaperType)
                .orderByDesc(WallpaperInfo::getId);
        return wallpaperInfoMapper.selectPage(page, wrapper);
    }

    public IPage<WallpaperInfo> publicList(String title, Long categoryId, Long tagId, String wallpaperType,
                                           String colorHex, String resolution, String sortBy, Integer pageNum, Integer pageSize) {
        Page<WallpaperInfo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<WallpaperInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(title), WallpaperInfo::getTitle, title)
                .eq(categoryId != null, WallpaperInfo::getCategoryId, categoryId)
                .eq(StringUtils.hasText(wallpaperType), WallpaperInfo::getWallpaperType, wallpaperType)
                .eq(StringUtils.hasText(colorHex), WallpaperInfo::getColorHex, colorHex)
                .eq(StringUtils.hasText(resolution), WallpaperInfo::getResolution, resolution)
                .eq(WallpaperInfo::getAuditStatus, 1)
                .eq(WallpaperInfo::getPublishStatus, 1)
                .eq(WallpaperInfo::getStatus, 1);
        if (tagId != null) {
            LambdaQueryWrapper<WallpaperTagRel> tagWrapper = new LambdaQueryWrapper<>();
            tagWrapper.eq(WallpaperTagRel::getTagId, tagId);
            List<WallpaperTagRel> relList = tagRelMapper.selectList(tagWrapper);
            List<Long> wallpaperIds = relList.stream().map(WallpaperTagRel::getWallpaperId).distinct().collect(Collectors.toList());
            if (wallpaperIds.isEmpty()) {
                page.setRecords(Collections.emptyList());
                page.setTotal(0);
                return page;
            }
            wrapper.in(WallpaperInfo::getId, wallpaperIds);
        }
        if ("hot".equals(sortBy)) {
            wrapper.orderByDesc(WallpaperInfo::getDownloadCount)
                    .orderByDesc(WallpaperInfo::getFavoriteCount)
                    .orderByDesc(WallpaperInfo::getId);
        } else {
            wrapper.orderByDesc(WallpaperInfo::getFeatured)
                    .orderByDesc(WallpaperInfo::getCreateTime)
                    .orderByDesc(WallpaperInfo::getId);
        }
        return wallpaperInfoMapper.selectPage(page, wrapper);
    }

    public Map<String, Object> detail(Long id, Long currentUserId) {
        WallpaperInfo wallpaper = wallpaperInfoMapper.selectById(id);
        if (wallpaper == null) {
            throw new BusinessException("壁纸不存在");
        }
        wallpaper.setViewCount((wallpaper.getViewCount() == null ? 0 : wallpaper.getViewCount()) + 1);
        wallpaperInfoMapper.updateById(wallpaper);
        Map<String, Object> result = new HashMap<>();
        result.put("wallpaper", wallpaper);
        result.put("category", categoryMapper.selectById(wallpaper.getCategoryId()));
        result.put("tags", getTagsByWallpaperId(id));
        result.put("hasFavorite", currentUserId != null && hasFavorite(id, currentUserId));
        return result;
    }

    public List<WallpaperInfo> recommend(Integer size) {
        int limit = size == null || size <= 0 ? 8 : size;
        LambdaQueryWrapper<WallpaperInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WallpaperInfo::getAuditStatus, 1)
                .eq(WallpaperInfo::getPublishStatus, 1)
                .eq(WallpaperInfo::getStatus, 1)
                .orderByDesc(WallpaperInfo::getFeatured)
                .orderByDesc(WallpaperInfo::getDownloadCount)
                .orderByDesc(WallpaperInfo::getId)
                .last("limit " + limit);
        return wallpaperInfoMapper.selectList(wrapper);
    }

    public IPage<WallpaperInfo> myList(Long userId, Integer pageNum, Integer pageSize) {
        Page<WallpaperInfo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<WallpaperInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WallpaperInfo::getUploaderId, userId).orderByDesc(WallpaperInfo::getId);
        return wallpaperInfoMapper.selectPage(page, wrapper);
    }

    public void add(Map<String, Object> params, Long userId) {
        SysUser user = authService.getById(userId);
        WallpaperInfo wallpaper = new WallpaperInfo();
        fillWallpaper(wallpaper, params, userId, "admin".equals(user.getRole()));
        wallpaperInfoMapper.insert(wallpaper);
        replaceTags(wallpaper.getId(), parseTagIds(params.get("tagIds")));
    }

    public void update(Map<String, Object> params, Long userId) {
        Long id = parseLong(params.get("id"));
        if (id == null) {
            throw new BusinessException("壁纸ID不能为空");
        }
        WallpaperInfo wallpaper = wallpaperInfoMapper.selectById(id);
        if (wallpaper == null) {
            throw new BusinessException("壁纸不存在");
        }
        boolean admin = authService.isAdmin(userId);
        if (!admin && !userId.equals(wallpaper.getUploaderId())) {
            throw new BusinessException("无权限编辑该壁纸");
        }
        fillWallpaper(wallpaper, params, wallpaper.getUploaderId(), admin);
        if (!admin) {
            wallpaper.setAuditStatus(0);
            wallpaper.setPublishStatus(0);
        }
        wallpaperInfoMapper.updateById(wallpaper);
        replaceTags(id, parseTagIds(params.get("tagIds")));
    }

    public void delete(Long id, Long userId) {
        WallpaperInfo wallpaper = wallpaperInfoMapper.selectById(id);
        if (wallpaper == null) {
            throw new BusinessException("壁纸不存在");
        }
        boolean admin = authService.isAdmin(userId);
        if (!admin && !userId.equals(wallpaper.getUploaderId())) {
            throw new BusinessException("无权限删除该壁纸");
        }
        wallpaperInfoMapper.deleteById(id);
        LambdaQueryWrapper<WallpaperTagRel> tagWrapper = new LambdaQueryWrapper<>();
        tagWrapper.eq(WallpaperTagRel::getWallpaperId, id);
        tagRelMapper.delete(tagWrapper);
        LambdaQueryWrapper<WallpaperFavorite> favoriteWrapper = new LambdaQueryWrapper<>();
        favoriteWrapper.eq(WallpaperFavorite::getWallpaperId, id);
        favoriteMapper.delete(favoriteWrapper);
        LambdaQueryWrapper<WallpaperDownload> downloadWrapper = new LambdaQueryWrapper<>();
        downloadWrapper.eq(WallpaperDownload::getWallpaperId, id);
        downloadMapper.delete(downloadWrapper);
    }

    public void publish(Long id, Integer publishStatus, Long userId) {
        authService.assertAdmin(userId);
        WallpaperInfo wallpaper = wallpaperInfoMapper.selectById(id);
        if (wallpaper == null) {
            throw new BusinessException("壁纸不存在");
        }
        if (wallpaper.getAuditStatus() == null || wallpaper.getAuditStatus() != 1) {
            throw new BusinessException("仅审核通过的壁纸可上架");
        }
        wallpaper.setPublishStatus(publishStatus);
        wallpaperInfoMapper.updateById(wallpaper);
    }

    public void download(Long wallpaperId, Long userId) {
        WallpaperInfo wallpaper = wallpaperInfoMapper.selectById(wallpaperId);
        if (wallpaper == null) {
            throw new BusinessException("壁纸不存在");
        }
        wallpaper.setDownloadCount((wallpaper.getDownloadCount() == null ? 0 : wallpaper.getDownloadCount()) + 1);
        wallpaperInfoMapper.updateById(wallpaper);
        WallpaperDownload download = new WallpaperDownload();
        download.setWallpaperId(wallpaperId);
        download.setUserId(userId);
        download.setSource("web");
        downloadMapper.insert(download);
    }

    public WallpaperInfo getById(Long id) {
        return wallpaperInfoMapper.selectById(id);
    }

    public List<WallpaperTag> getTagsByWallpaperId(Long wallpaperId) {
        LambdaQueryWrapper<WallpaperTagRel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WallpaperTagRel::getWallpaperId, wallpaperId);
        List<WallpaperTagRel> relList = tagRelMapper.selectList(wrapper);
        if (relList.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> tagIds = relList.stream().map(WallpaperTagRel::getTagId).collect(Collectors.toList());
        return tagMapper.selectBatchIds(tagIds);
    }

    public boolean hasFavorite(Long wallpaperId, Long userId) {
        LambdaQueryWrapper<WallpaperFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WallpaperFavorite::getWallpaperId, wallpaperId)
                .eq(WallpaperFavorite::getUserId, userId)
                .last("limit 1");
        return favoriteMapper.selectOne(wrapper) != null;
    }

    private void fillWallpaper(WallpaperInfo wallpaper, Map<String, Object> params, Long uploaderId, boolean admin) {
        String title = text(params.get("title"));
        Long categoryId = parseLong(params.get("categoryId"));
        String imageUrl = text(params.get("imageUrl"));
        if (!StringUtils.hasText(title) || categoryId == null || !StringUtils.hasText(imageUrl)) {
            throw new BusinessException("标题、分类和原图不能为空");
        }
        WallpaperCategory category = categoryMapper.selectById(categoryId);
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        SysUser user = authService.getById(uploaderId);
        wallpaper.setTitle(title);
        wallpaper.setSubtitle(text(params.get("subtitle")));
        wallpaper.setCategoryId(categoryId);
        wallpaper.setImageUrl(imageUrl);
        wallpaper.setCoverUrl(StringUtils.hasText(text(params.get("coverUrl"))) ? text(params.get("coverUrl")) : imageUrl);
        wallpaper.setPreviewUrl(StringUtils.hasText(text(params.get("previewUrl"))) ? text(params.get("previewUrl")) : imageUrl);
        wallpaper.setWallpaperType(StringUtils.hasText(text(params.get("wallpaperType"))) ? text(params.get("wallpaperType")) : "pc");
        wallpaper.setResolution(text(params.get("resolution")));
        wallpaper.setWidth(parseInt(params.get("width"), 0));
        wallpaper.setHeight(parseInt(params.get("height"), 0));
        wallpaper.setColorHex(text(params.get("colorHex")));
        wallpaper.setFileFormat(StringUtils.hasText(text(params.get("fileFormat"))) ? text(params.get("fileFormat")) : "jpg");
        wallpaper.setFileSize(parseLong(params.get("fileSize"), 0L));
        wallpaper.setSourceType(admin ? "official" : "user_upload");
        wallpaper.setAuthorName(StringUtils.hasText(text(params.get("authorName"))) ? text(params.get("authorName")) : user.getRealName());
        wallpaper.setUploaderId(uploaderId);
        wallpaper.setDescription(text(params.get("description")));
        wallpaper.setFeatured(parseInt(params.get("featured"), 0));
        wallpaper.setStatus(parseInt(params.get("status"), 1));
        if (wallpaper.getDownloadCount() == null) {
            wallpaper.setDownloadCount(0);
        }
        if (wallpaper.getFavoriteCount() == null) {
            wallpaper.setFavoriteCount(0);
        }
        if (wallpaper.getViewCount() == null) {
            wallpaper.setViewCount(0);
        }
        if (admin) {
            wallpaper.setAuditStatus(parseInt(params.get("auditStatus"), 1));
            wallpaper.setPublishStatus(parseInt(params.get("publishStatus"), 1));
        } else {
            wallpaper.setAuditStatus(0);
            wallpaper.setPublishStatus(0);
        }
    }

    private void replaceTags(Long wallpaperId, List<Long> tagIds) {
        LambdaQueryWrapper<WallpaperTagRel> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(WallpaperTagRel::getWallpaperId, wallpaperId);
        tagRelMapper.delete(deleteWrapper);
        if (tagIds == null || tagIds.isEmpty()) {
            return;
        }
        for (Long tagId : tagIds) {
            WallpaperTagRel rel = new WallpaperTagRel();
            rel.setWallpaperId(wallpaperId);
            rel.setTagId(tagId);
            tagRelMapper.insert(rel);
        }
    }

    private List<Long> parseTagIds(Object raw) {
        if (raw == null) {
            return Collections.emptyList();
        }
        List<Long> result = new ArrayList<>();
        if (raw instanceof Collection) {
            for (Object item : (Collection<?>) raw) {
                Long value = parseLong(item);
                if (value != null) {
                    result.add(value);
                }
            }
            return result;
        }
        String text = String.valueOf(raw).replace("[", "").replace("]", "");
        if (!StringUtils.hasText(text)) {
            return Collections.emptyList();
        }
        String[] parts = text.split(",");
        for (String part : parts) {
            if (StringUtils.hasText(part)) {
                result.add(Long.valueOf(part.trim()));
            }
        }
        return result;
    }

    private String text(Object value) {
        return value == null ? null : String.valueOf(value).trim();
    }

    private Long parseLong(Object value) {
        if (value == null || !StringUtils.hasText(String.valueOf(value))) {
            return null;
        }
        return Long.valueOf(String.valueOf(value));
    }

    private Long parseLong(Object value, Long defaultValue) {
        Long result = parseLong(value);
        return result == null ? defaultValue : result;
    }

    private Integer parseInt(Object value, Integer defaultValue) {
        if (value == null || !StringUtils.hasText(String.valueOf(value))) {
            return defaultValue;
        }
        return Integer.valueOf(String.valueOf(value));
    }
}
