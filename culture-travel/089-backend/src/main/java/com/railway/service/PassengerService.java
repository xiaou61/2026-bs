package com.railway.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.railway.common.BusinessException;
import com.railway.common.PageResult;
import com.railway.entity.PassengerProfile;
import com.railway.mapper.PassengerProfileMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PassengerService {

    @Resource
    private PassengerProfileMapper passengerProfileMapper;

    public PageResult<PassengerProfile> pageByUser(Long userId, Integer pageNum, Integer pageSize, String passengerName) {
        Page<PassengerProfile> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<PassengerProfile> wrapper = new LambdaQueryWrapper<PassengerProfile>()
                .eq(PassengerProfile::getUserId, userId)
                .like(StringUtils.hasText(passengerName), PassengerProfile::getPassengerName, passengerName == null ? null : passengerName.trim())
                .orderByDesc(PassengerProfile::getIsDefault)
                .orderByDesc(PassengerProfile::getId);
        Page<PassengerProfile> resultPage = passengerProfileMapper.selectPage(page, wrapper);
        PageResult<PassengerProfile> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<PassengerProfile> listByUser(Long userId) {
        return passengerProfileMapper.selectList(new LambdaQueryWrapper<PassengerProfile>()
                .eq(PassengerProfile::getUserId, userId)
                .orderByDesc(PassengerProfile::getIsDefault)
                .orderByDesc(PassengerProfile::getId));
    }

    public void save(Long userId, PassengerProfile profile) {
        validate(profile);
        assertIdCardUnique(profile.getIdCard(), profile.getId());
        if (profile.getIsDefault() != null && profile.getIsDefault() == 1) {
            clearDefault(userId);
        }
        if (profile.getId() == null) {
            profile.setUserId(userId);
            profile.setPassengerName(profile.getPassengerName().trim());
            profile.setIdCard(profile.getIdCard().trim());
            profile.setPhone(StringUtils.hasText(profile.getPhone()) ? profile.getPhone().trim() : null);
            profile.setPassengerType(profile.getPassengerType().trim());
            profile.setIsDefault(profile.getIsDefault() == null ? 0 : profile.getIsDefault());
            passengerProfileMapper.insert(profile);
            return;
        }
        PassengerProfile db = getOwnedById(userId, profile.getId());
        db.setPassengerName(profile.getPassengerName().trim());
        db.setIdCard(profile.getIdCard().trim());
        db.setPhone(StringUtils.hasText(profile.getPhone()) ? profile.getPhone().trim() : null);
        db.setPassengerType(profile.getPassengerType().trim());
        db.setIsDefault(profile.getIsDefault() == null ? 0 : profile.getIsDefault());
        passengerProfileMapper.updateById(db);
    }

    public void delete(Long userId, Long id) {
        getOwnedById(userId, id);
        passengerProfileMapper.deleteById(id);
    }

    public List<PassengerProfile> getByIds(Long userId, List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException("乘车人不能为空");
        }
        List<PassengerProfile> list = passengerProfileMapper.selectList(new LambdaQueryWrapper<PassengerProfile>()
                .eq(PassengerProfile::getUserId, userId)
                .in(PassengerProfile::getId, ids)
                .orderByAsc(PassengerProfile::getId));
        if (list.size() != ids.size()) {
            throw new BusinessException("存在无效乘车人");
        }
        return list;
    }

    private PassengerProfile getOwnedById(Long userId, Long id) {
        PassengerProfile profile = passengerProfileMapper.selectById(id);
        if (profile == null || !profile.getUserId().equals(userId)) {
            throw new BusinessException("乘车人不存在");
        }
        return profile;
    }

    private void clearDefault(Long userId) {
        List<PassengerProfile> list = passengerProfileMapper.selectList(new LambdaQueryWrapper<PassengerProfile>()
                .eq(PassengerProfile::getUserId, userId)
                .eq(PassengerProfile::getIsDefault, 1));
        for (PassengerProfile item : list) {
            item.setIsDefault(0);
            passengerProfileMapper.updateById(item);
        }
    }

    private void validate(PassengerProfile profile) {
        if (profile == null || !StringUtils.hasText(profile.getPassengerName()) || !StringUtils.hasText(profile.getIdCard())) {
            throw new BusinessException("乘车人姓名和证件号不能为空");
        }
        if (!StringUtils.hasText(profile.getPassengerType())) {
            throw new BusinessException("乘车人类型不能为空");
        }
    }

    private void assertIdCardUnique(String idCard, Long excludeId) {
        PassengerProfile exist = passengerProfileMapper.selectOne(new LambdaQueryWrapper<PassengerProfile>()
                .eq(PassengerProfile::getIdCard, idCard.trim())
                .last("limit 1"));
        if (exist != null && (excludeId == null || !exist.getId().equals(excludeId))) {
            throw new BusinessException("证件号已存在");
        }
    }
}
