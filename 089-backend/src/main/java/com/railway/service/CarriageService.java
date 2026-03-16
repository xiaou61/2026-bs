package com.railway.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.railway.common.BusinessException;
import com.railway.common.PageResult;
import com.railway.entity.Carriage;
import com.railway.mapper.CarriageMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CarriageService {

    @Resource
    private CarriageMapper carriageMapper;

    public PageResult<Carriage> page(Integer pageNum, Integer pageSize, String templateName, String seatType, Integer status) {
        Page<Carriage> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Carriage> wrapper = new LambdaQueryWrapper<Carriage>()
                .like(StringUtils.hasText(templateName), Carriage::getTemplateName, trim(templateName))
                .eq(StringUtils.hasText(seatType), Carriage::getSeatType, trim(seatType))
                .eq(status != null, Carriage::getStatus, status)
                .orderByDesc(Carriage::getId);
        Page<Carriage> resultPage = carriageMapper.selectPage(page, wrapper);
        PageResult<Carriage> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<Carriage> enabledList() {
        return carriageMapper.selectList(new LambdaQueryWrapper<Carriage>()
                .eq(Carriage::getStatus, 1)
                .orderByAsc(Carriage::getTemplateName));
    }

    public Carriage getById(Long id) {
        Carriage carriage = carriageMapper.selectById(id);
        if (carriage == null) {
            throw new BusinessException("车厢模板不存在");
        }
        return carriage;
    }

    public void save(Carriage carriage) {
        validate(carriage);
        if (carriage.getId() == null) {
            assertUniqueName(carriage.getTemplateName(), null);
            carriage.setTemplateName(carriage.getTemplateName().trim());
            carriage.setSeatType(carriage.getSeatType().trim());
            carriage.setStatus(carriage.getStatus() == null ? 1 : carriage.getStatus());
            carriageMapper.insert(carriage);
            return;
        }
        Carriage db = getById(carriage.getId());
        assertUniqueName(carriage.getTemplateName(), db.getId());
        db.setTemplateName(carriage.getTemplateName().trim());
        db.setSeatType(carriage.getSeatType().trim());
        db.setCoachCount(carriage.getCoachCount());
        db.setSeatRows(carriage.getSeatRows());
        db.setSeatCols(carriage.getSeatCols());
        db.setPriceFactor(carriage.getPriceFactor());
        db.setStatus(carriage.getStatus() == null ? db.getStatus() : carriage.getStatus());
        carriageMapper.updateById(db);
    }

    public void deleteById(Long id) {
        carriageMapper.deleteById(id);
    }

    private void validate(Carriage carriage) {
        if (carriage == null || !StringUtils.hasText(carriage.getTemplateName()) || !StringUtils.hasText(carriage.getSeatType())) {
            throw new BusinessException("模板名称和座席类型不能为空");
        }
        if (carriage.getCoachCount() == null || carriage.getCoachCount() <= 0) {
            throw new BusinessException("车厢数量必须大于0");
        }
        if (carriage.getSeatRows() == null || carriage.getSeatRows() <= 0 || carriage.getSeatCols() == null || carriage.getSeatCols() <= 0) {
            throw new BusinessException("座席布局不合法");
        }
        if (carriage.getPriceFactor() == null || carriage.getPriceFactor().compareTo(BigDecimal.ZERO) <= 0) {
            carriage.setPriceFactor(BigDecimal.ONE);
        }
    }

    private void assertUniqueName(String templateName, Long excludeId) {
        Carriage exist = carriageMapper.selectOne(new LambdaQueryWrapper<Carriage>()
                .eq(Carriage::getTemplateName, templateName.trim())
                .last("limit 1"));
        if (exist != null && (excludeId == null || !exist.getId().equals(excludeId))) {
            throw new BusinessException("模板名称已存在");
        }
    }

    private String trim(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }
}
