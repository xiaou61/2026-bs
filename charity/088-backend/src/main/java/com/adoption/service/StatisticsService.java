package com.adoption.service;

import com.adoption.entity.AdoptionAgreement;
import com.adoption.entity.AdoptionApplication;
import com.adoption.entity.ChildProfile;
import com.adoption.entity.FollowUpRecord;
import com.adoption.entity.SystemNotice;
import com.adoption.mapper.AdoptionAgreementMapper;
import com.adoption.mapper.AdoptionApplicationMapper;
import com.adoption.mapper.ChildProfileMapper;
import com.adoption.mapper.FollowUpRecordMapper;
import com.adoption.mapper.SystemNoticeMapper;
import com.adoption.mapper.SysUserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private ChildProfileMapper childProfileMapper;

    @Autowired
    private AdoptionApplicationMapper adoptionApplicationMapper;

    @Autowired
    private AdoptionAgreementMapper adoptionAgreementMapper;

    @Autowired
    private FollowUpRecordMapper followUpRecordMapper;

    @Autowired
    private SystemNoticeMapper systemNoticeMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    public Map<String, Object> dashboard(String role, Long userId) {
        Map<String, Object> data = new HashMap<>();
        if ("applicant".equals(role)) {
            data.put("myApplicationCount", adoptionApplicationMapper.selectCount(Wrappers.<AdoptionApplication>lambdaQuery().eq(AdoptionApplication::getApplicantId, userId)));
            data.put("completedCount", adoptionApplicationMapper.selectCount(Wrappers.<AdoptionApplication>lambdaQuery().eq(AdoptionApplication::getApplicantId, userId).eq(AdoptionApplication::getStatus, 5)));
            data.put("noticeCount", systemNoticeMapper.selectCount(Wrappers.<SystemNotice>lambdaQuery().eq(SystemNotice::getStatus, 1)));
            data.put("openChildCount", childProfileMapper.selectCount(Wrappers.<ChildProfile>lambdaQuery().eq(ChildProfile::getAdoptionStatus, 0)));
            return data;
        }
        data.put("userCount", sysUserMapper.selectCount(null));
        data.put("childCount", childProfileMapper.selectCount(null));
        data.put("openChildCount", childProfileMapper.selectCount(Wrappers.<ChildProfile>lambdaQuery().eq(ChildProfile::getAdoptionStatus, 0)));
        data.put("applicationCount", adoptionApplicationMapper.selectCount(null));
        data.put("matchedCount", adoptionAgreementMapper.selectCount(Wrappers.<AdoptionAgreement>lambdaQuery().eq(AdoptionAgreement::getSignStatus, 1)));
        data.put("followCount", followUpRecordMapper.selectCount(null));
        data.put("noticeCount", systemNoticeMapper.selectCount(Wrappers.<SystemNotice>lambdaQuery().eq(SystemNotice::getStatus, 1)));
        return data;
    }
}
