package com.esgreport.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.esgreport.entity.DisclosureTemplate;
import com.esgreport.mapper.DisclosureTemplateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DisclosureTemplateService {
    private final DisclosureTemplateMapper disclosureTemplateMapper;

    public PageInfo<DisclosureTemplate> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(disclosureTemplateMapper.selectPage(keyword, status));
    }

    public void save(DisclosureTemplate entity) {
        if (entity.getId() == null) disclosureTemplateMapper.insert(entity);
        else disclosureTemplateMapper.update(entity);
    }

    public void delete(Long id) {
        disclosureTemplateMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        disclosureTemplateMapper.updateStatus(id, status);
    }
}
