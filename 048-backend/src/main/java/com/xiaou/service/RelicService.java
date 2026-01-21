package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.dto.RelicDTO;
import com.xiaou.entity.Relic;
import com.xiaou.entity.RelicImage;
import java.util.List;

public interface RelicService {
    Page<Relic> page(int current, int size, String keyword, Long categoryId, Long dynastyId, Integer level);
    Relic getById(Long id);
    List<RelicImage> getImages(Long relicId);
    void save(RelicDTO dto);
    void update(RelicDTO dto);
    void updateStatus(Long id, Integer status);
    void addViewCount(Long id);
    void addLikeCount(Long id);
}
