package com.petcafe.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petcafe.common.BusinessException;
import com.petcafe.common.PageResult;
import com.petcafe.entity.CafeArea;
import com.petcafe.entity.CafeShop;
import com.petcafe.mapper.CafeAreaMapper;
import com.petcafe.mapper.CafeShopMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AreaService {

    @Resource
    private CafeAreaMapper areaMapper;

    @Resource
    private CafeShopMapper shopMapper;

    public PageResult<CafeArea> page(Integer pageNum, Integer pageSize, String name, Integer status) {
        Page<CafeArea> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CafeArea> wrapper = new LambdaQueryWrapper<CafeArea>()
                .like(StringUtils.hasText(name), CafeArea::getName, name == null ? null : name.trim())
                .eq(status != null, CafeArea::getStatus, status)
                .orderByDesc(CafeArea::getId);
        Page<CafeArea> resultPage = areaMapper.selectPage(page, wrapper);
        PageResult<CafeArea> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<CafeArea> listAll() {
        return areaMapper.selectList(new LambdaQueryWrapper<CafeArea>()
                .eq(CafeArea::getStatus, 1)
                .orderByDesc(CafeArea::getId));
    }

    public CafeArea getById(Long id) {
        CafeArea area = areaMapper.selectById(id);
        if (area == null) {
            throw new BusinessException("区域不存在");
        }
        return area;
    }

    public void save(CafeArea area) {
        if (area == null || !StringUtils.hasText(area.getName())) {
            throw new BusinessException("区域名称不能为空");
        }
        if (area.getId() == null) {
            area.setStatus(area.getStatus() == null ? 1 : area.getStatus());
            areaMapper.insert(area);
            return;
        }
        CafeArea db = getById(area.getId());
        db.setAreaNo(StringUtils.hasText(area.getAreaNo()) ? area.getAreaNo().trim() : db.getAreaNo());
        db.setName(area.getName().trim());
        db.setCity(StringUtils.hasText(area.getCity()) ? area.getCity().trim() : null);
        db.setDistrict(StringUtils.hasText(area.getDistrict()) ? area.getDistrict().trim() : null);
        db.setAddress(StringUtils.hasText(area.getAddress()) ? area.getAddress().trim() : null);
        db.setContactPerson(StringUtils.hasText(area.getContactPerson()) ? area.getContactPerson().trim() : null);
        db.setContactPhone(StringUtils.hasText(area.getContactPhone()) ? area.getContactPhone().trim() : null);
        db.setRemark(StringUtils.hasText(area.getRemark()) ? area.getRemark().trim() : null);
        if (area.getStatus() != null) {
            db.setStatus(area.getStatus());
        }
        areaMapper.updateById(db);
    }

    public void deleteById(Long id) {
        if (shopMapper.selectCount(new LambdaQueryWrapper<CafeShop>().eq(CafeShop::getAreaId, id)) > 0) {
            throw new BusinessException("该区域下存在门店，无法删除");
        }
        areaMapper.deleteById(id);
    }
}
