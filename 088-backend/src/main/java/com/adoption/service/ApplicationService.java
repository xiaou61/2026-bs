package com.adoption.service;

import com.adoption.common.BusinessException;
import com.adoption.entity.AdopterProfile;
import com.adoption.entity.AdoptionApplication;
import com.adoption.entity.ApplicationMaterial;
import com.adoption.entity.ApprovalRecord;
import com.adoption.entity.ChildProfile;
import com.adoption.entity.SysUser;
import com.adoption.mapper.AdopterProfileMapper;
import com.adoption.mapper.AdoptionApplicationMapper;
import com.adoption.mapper.ApplicationMaterialMapper;
import com.adoption.mapper.ApprovalRecordMapper;
import com.adoption.mapper.ChildProfileMapper;
import com.adoption.mapper.SysUserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationService {

    @Autowired
    private AdoptionApplicationMapper adoptionApplicationMapper;

    @Autowired
    private ApplicationMaterialMapper applicationMaterialMapper;

    @Autowired
    private ApprovalRecordMapper approvalRecordMapper;

    @Autowired
    private ChildProfileMapper childProfileMapper;

    @Autowired
    private AdopterProfileMapper adopterProfileMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    public Map<String, Object> page(Integer pageNum, Integer pageSize, Integer status) {
        Page<AdoptionApplication> page = new Page<>(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        Page<AdoptionApplication> result = adoptionApplicationMapper.selectPage(page, Wrappers.<AdoptionApplication>lambdaQuery()
                .eq(status != null, AdoptionApplication::getStatus, status)
                .orderByDesc(AdoptionApplication::getCreateTime));
        fillNames(result.getRecords());
        return buildPage(result);
    }

    public Map<String, Object> myPage(Long userId, Integer pageNum, Integer pageSize) {
        Page<AdoptionApplication> page = new Page<>(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        Page<AdoptionApplication> result = adoptionApplicationMapper.selectPage(page, Wrappers.<AdoptionApplication>lambdaQuery()
                .eq(AdoptionApplication::getApplicantId, userId)
                .orderByDesc(AdoptionApplication::getCreateTime));
        fillNames(result.getRecords());
        return buildPage(result);
    }

    public void add(Long userId, AdoptionApplication application) {
        if (application.getChildId() == null || !StringUtils.hasText(application.getReason())) {
            throw new BusinessException("申请信息不完整");
        }
        ChildProfile child = childProfileMapper.selectById(application.getChildId());
        if (child == null || child.getAdoptionStatus() == null || child.getAdoptionStatus() != 0) {
            throw new BusinessException("当前儿童不可申请收养");
        }
        AdopterProfile profile = adopterProfileMapper.selectOne(Wrappers.<AdopterProfile>lambdaQuery().eq(AdopterProfile::getUserId, userId));
        if (profile == null || !StringUtils.hasText(profile.getAddress()) || !StringUtils.hasText(profile.getAdoptionReason())) {
            throw new BusinessException("请先完善申请人资料");
        }
        Long exists = adoptionApplicationMapper.selectCount(Wrappers.<AdoptionApplication>lambdaQuery()
                .eq(AdoptionApplication::getChildId, application.getChildId())
                .eq(AdoptionApplication::getApplicantId, userId)
                .notIn(AdoptionApplication::getStatus, 6));
        if (exists != null && exists > 0) {
            throw new BusinessException("已存在处理中申请");
        }
        application.setApplicantId(userId);
        application.setApplicationNo("AP" + System.currentTimeMillis());
        application.setStatus(1);
        application.setSubmitTime(LocalDateTime.now());
        adoptionApplicationMapper.insert(application);
        ApprovalRecord record = new ApprovalRecord();
        record.setApplicationId(application.getId());
        record.setNodeName("材料初审");
        record.setApprovalStatus(0);
        approvalRecordMapper.insert(record);
    }

    public void review(Long reviewerId, Long applicationId, Integer reviewStatus, String reviewRemark) {
        AdoptionApplication application = adoptionApplicationMapper.selectById(applicationId);
        if (application == null) {
            throw new BusinessException("申请不存在");
        }
        application.setReviewRemark(reviewRemark);
        application.setReviewTime(LocalDateTime.now());
        application.setStatus(reviewStatus != null && reviewStatus == 1 ? 2 : 6);
        adoptionApplicationMapper.updateById(application);
        ApprovalRecord record = new ApprovalRecord();
        record.setApplicationId(applicationId);
        record.setNodeName("材料初审");
        record.setApprovalStatus(reviewStatus);
        record.setApprovalRemark(reviewRemark);
        record.setApproverId(reviewerId);
        approvalRecordMapper.insert(record);
    }

    public List<ApplicationMaterial> getMaterialList(Long applicationId) {
        return applicationMaterialMapper.selectList(Wrappers.<ApplicationMaterial>lambdaQuery()
                .eq(ApplicationMaterial::getApplicationId, applicationId)
                .orderByDesc(ApplicationMaterial::getCreateTime));
    }

    public void addMaterial(ApplicationMaterial material) {
        if (material.getApplicationId() == null || !StringUtils.hasText(material.getMaterialName())) {
            throw new BusinessException("材料信息不完整");
        }
        material.setReviewStatus(material.getReviewStatus() == null ? 0 : material.getReviewStatus());
        applicationMaterialMapper.insert(material);
    }

    private void fillNames(List<AdoptionApplication> list) {
        for (AdoptionApplication item : list) {
            ChildProfile child = childProfileMapper.selectById(item.getChildId());
            SysUser user = sysUserMapper.selectById(item.getApplicantId());
            item.setChildName(child == null ? "" : child.getName());
            item.setApplicantName(user == null ? "" : user.getRealName());
        }
    }

    private Map<String, Object> buildPage(Page<AdoptionApplication> page) {
        Map<String, Object> data = new HashMap<>();
        data.put("list", page.getRecords());
        data.put("total", page.getTotal());
        return data;
    }
}
