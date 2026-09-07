package com.citytoilet.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.citytoilet.entity.ToiletSite;
import com.citytoilet.mapper.ToiletSiteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToiletSiteService {
    private final ToiletSiteMapper mapper;

    public PageInfo<ToiletSite> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public void save(ToiletSite entity) {
        if (entity.getId() == null) mapper.insert(entity);
        else mapper.update(entity);
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        mapper.updateStatus(id, status);
    }
}
