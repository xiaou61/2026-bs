package com.adoption.service;

import com.adoption.common.BusinessException;
import com.adoption.entity.AdoptionAgreement;
import com.adoption.entity.AdoptionApplication;
import com.adoption.entity.ChildProfile;
import com.adoption.entity.SysUser;
import com.adoption.mapper.AdoptionAgreementMapper;
import com.adoption.mapper.AdoptionApplicationMapper;
import com.adoption.mapper.ChildProfileMapper;
import com.adoption.mapper.SysUserMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AgreementService {

    @Autowired
    private AdoptionAgreementMapper adoptionAgreementMapper;

    @Autowired
    private AdoptionApplicationMapper adoptionApplicationMapper;

    @Autowired
    private ChildProfileMapper childProfileMapper;

    @Autowired
    private SysUserMapper sysUserMapper;
    public Map<String, Object> page(Integer pageNum, Integer pageSize) {
        Page<AdoptionAgreement> page = new Page<>(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        Page<AdoptionAgreement> result = adoptionAgreementMapper.selectPage(page, Wrappers.<AdoptionAgreement>lambdaQuery().orderByDesc(AdoptionAgreement::getCreateTime));
        for (AdoptionAgreement item : result.getRecords()) {
            ChildProfile child = childProfileMapper.selectById(item.getChildId());
            SysUser applicant = sysUserMapper.selectById(item.getApplicantId());
            item.setChildName(child == null ? "" : child.getName());
            item.setApplicantName(applicant == null ? "" : applicant.getRealName());
        }
        Map<String, Object> data = new HashMap<>();
        data.put("list", result.getRecords());
        data.put("total", result.getTotal());
        return data;
    }

    public void add(AdoptionAgreement agreement) {
        if (agreement.getApplicationId() == null) {
            throw new BusinessException("协议信息不完整");
        }
        AdoptionApplication application = adoptionApplicationMapper.selectById(agreement.getApplicationId());
        if (application == null) {
            throw new BusinessException("申请不存在");
        }
        agreement.setAgreementNo("AG" + System.currentTimeMillis());
        agreement.setChildId(application.getChildId());
        agreement.setApplicantId(application.getApplicantId());
        adoptionAgreementMapper.insert(agreement);
        if (agreement.getSignStatus() != null && agreement.getSignStatus() == 1) {
            application.setStatus(5);
            adoptionApplicationMapper.updateById(application);
            ChildProfile child = childProfileMapper.selectById(application.getChildId());
            if (child != null) {
                child.setAdoptionStatus(2);
                childProfileMapper.updateById(child);
            }
        }
    }

    public void update(AdoptionAgreement agreement) {
        adoptionAgreementMapper.updateById(agreement);
        if (agreement.getSignStatus() != null && agreement.getSignStatus() == 1) {
            AdoptionApplication application = adoptionApplicationMapper.selectById(agreement.getApplicationId());
            if (application != null) {
                application.setStatus(5);
                adoptionApplicationMapper.updateById(application);
                ChildProfile child = childProfileMapper.selectById(application.getChildId());
                if (child != null) {
                    child.setAdoptionStatus(2);
                    childProfileMapper.updateById(child);
                }
            }
        }
    }
}
