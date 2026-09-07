package com.classic.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.classic.common.BusinessException;
import com.classic.common.PageResult;
import com.classic.entity.FormulaInfo;
import com.classic.mapper.FormulaInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FormulaInfoService {

    @Resource
    private FormulaInfoMapper formulaInfoMapper;

    public List<FormulaInfo> list() {
        return formulaInfoMapper.selectList(new LambdaQueryWrapper<FormulaInfo>().eq(FormulaInfo::getStatus, 1).orderByDesc(FormulaInfo::getId));
    }

    public List<FormulaInfo> listAllForMap() {
        return formulaInfoMapper.selectList(new LambdaQueryWrapper<>());
    }

    public PageResult<FormulaInfo> page(Integer pageNum, Integer pageSize, String name, Integer status) {
        LambdaQueryWrapper<FormulaInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null && !name.trim().isEmpty(), FormulaInfo::getName, name == null ? null : name.trim());
        wrapper.eq(status != null, FormulaInfo::getStatus, status);
        wrapper.orderByDesc(FormulaInfo::getId);
        Page<FormulaInfo> page = formulaInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        PageResult<FormulaInfo> result = new PageResult<>();
        result.setTotal(page.getTotal());
        result.setRecords(page.getRecords());
        return result;
    }

    public void save(FormulaInfo formulaInfo) {
        if (formulaInfo == null || formulaInfo.getName() == null || formulaInfo.getName().trim().isEmpty()) {
            throw new BusinessException("经方名称不能为空");
        }
        formulaInfo.setName(formulaInfo.getName().trim());
        formulaInfo.setSource(formulaInfo.getSource() == null ? null : formulaInfo.getSource().trim());
        formulaInfo.setIndication(formulaInfo.getIndication() == null ? null : formulaInfo.getIndication().trim());
        formulaInfo.setComposition(formulaInfo.getComposition() == null ? null : formulaInfo.getComposition().trim());
        formulaInfo.setUsageMethod(formulaInfo.getUsageMethod() == null ? null : formulaInfo.getUsageMethod().trim());
        formulaInfo.setStatus(formulaInfo.getStatus() == null ? 1 : formulaInfo.getStatus());
        if (formulaInfo.getId() == null) {
            formulaInfoMapper.insert(formulaInfo);
        } else {
            if (formulaInfoMapper.selectById(formulaInfo.getId()) == null) {
                throw new BusinessException("经方不存在");
            }
            formulaInfoMapper.updateById(formulaInfo);
        }
    }

    public void deleteById(Long id) {
        formulaInfoMapper.deleteById(id);
    }

    public Long countAll() {
        return formulaInfoMapper.selectCount(new LambdaQueryWrapper<>());
    }
}
