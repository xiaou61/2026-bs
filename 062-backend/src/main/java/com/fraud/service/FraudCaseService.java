package com.fraud.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fraud.common.BusinessException;
import com.fraud.entity.FraudAlert;
import com.fraud.entity.FraudCase;
import com.fraud.entity.User;
import com.fraud.mapper.FraudAlertMapper;
import com.fraud.mapper.FraudCaseMapper;
import com.fraud.mapper.UserMapper;
import com.fraud.vo.FraudCaseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FraudCaseService {

    @Resource
    private FraudCaseMapper caseMapper;

    @Resource
    private FraudAlertMapper alertMapper;

    @Resource
    private UserMapper userMapper;

    public Page<FraudCaseVO> page(Integer pageNum, Integer pageSize, String caseNo, Integer status, Integer priority, Long ownerId) {
        QueryWrapper<FraudCase> wrapper = new QueryWrapper<>();
        if (caseNo != null && !caseNo.trim().isEmpty()) {
            wrapper.like("case_no", caseNo.trim());
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (priority != null) {
            wrapper.eq("priority", priority);
        }
        if (ownerId != null) {
            wrapper.eq("owner_id", ownerId);
        }
        wrapper.orderByDesc("id");
        Page<FraudCase> page = caseMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return convertPage(page);
    }

    public FraudCaseVO detail(Long id) {
        FraudCase fraudCase = caseMapper.selectById(id);
        if (fraudCase == null) {
            throw new BusinessException("案件不存在");
        }
        List<FraudCase> list = new ArrayList<>();
        list.add(fraudCase);
        List<FraudCaseVO> voList = convertList(list);
        return voList.isEmpty() ? null : voList.get(0);
    }

    public void save(FraudCase fraudCase, Long operatorId, String role) {
        if (fraudCase == null) {
            throw new BusinessException("案件参数不能为空");
        }
        if (fraudCase.getCaseType() == null || fraudCase.getCaseType().trim().isEmpty()) {
            throw new BusinessException("案件类型不能为空");
        }
        if (fraudCase.getSummary() != null) {
            fraudCase.setSummary(fraudCase.getSummary().trim());
            if (fraudCase.getSummary().length() > 500) {
                throw new BusinessException("案件摘要不能超过500字符");
            }
        }
        fraudCase.setCaseType(fraudCase.getCaseType().trim());
        if (fraudCase.getCaseType().length() > 40) {
            throw new BusinessException("案件类型不能超过40字符");
        }
        if (fraudCase.getPriority() == null || fraudCase.getPriority() < 1 || fraudCase.getPriority() > 3) {
            fraudCase.setPriority(2);
        }
        if (fraudCase.getId() == null) {
            if (fraudCase.getAlertId() == null) {
                throw new BusinessException("请选择预警");
            }
            FraudAlert alert = alertMapper.selectById(fraudCase.getAlertId());
            if (alert == null) {
                throw new BusinessException("关联预警不存在");
            }
            Long count = caseMapper.selectCount(new QueryWrapper<FraudCase>().eq("alert_id", fraudCase.getAlertId()));
            if (count != null && count > 0) {
                throw new BusinessException("该预警已有关联案件");
            }
            fraudCase.setCaseNo("CS" + IdUtil.getSnowflakeNextIdStr());
            fraudCase.setStatus(fraudCase.getStatus() == null ? 0 : fraudCase.getStatus());
            fraudCase.setOwnerId(fraudCase.getOwnerId() == null ? operatorId : fraudCase.getOwnerId());
            caseMapper.insert(fraudCase);
        } else {
            FraudCase db = caseMapper.selectById(fraudCase.getId());
            if (db == null) {
                throw new BusinessException("案件不存在");
            }
            if (!"ADMIN".equals(role) && (db.getOwnerId() == null || !db.getOwnerId().equals(operatorId))) {
                throw new BusinessException("无权限修改该案件");
            }
            fraudCase.setCaseNo(db.getCaseNo());
            fraudCase.setAlertId(db.getAlertId());
            if (fraudCase.getOwnerId() == null) {
                fraudCase.setOwnerId(db.getOwnerId());
            }
            if (fraudCase.getStatus() == null) {
                fraudCase.setStatus(db.getStatus());
            }
            if (fraudCase.getResult() != null) {
                fraudCase.setResult(fraudCase.getResult().trim());
                if (fraudCase.getResult().length() > 500) {
                    throw new BusinessException("处理结论不能超过500字符");
                }
            }
            caseMapper.updateById(fraudCase);
        }
    }

    public void close(Long id, Long operatorId, String role, String result) {
        FraudCase fraudCase = caseMapper.selectById(id);
        if (fraudCase == null) {
            throw new BusinessException("案件不存在");
        }
        if (!"ADMIN".equals(role) && (fraudCase.getOwnerId() == null || !fraudCase.getOwnerId().equals(operatorId))) {
            throw new BusinessException("无权限关闭该案件");
        }
        if (result != null) {
            result = result.trim();
            if (result.length() > 500) {
                throw new BusinessException("处理结论不能超过500字符");
            }
        }
        fraudCase.setStatus(2);
        fraudCase.setResult(result);
        fraudCase.setCloseTime(LocalDateTime.now());
        caseMapper.updateById(fraudCase);
    }

    private Page<FraudCaseVO> convertPage(Page<FraudCase> page) {
        Page<FraudCaseVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(convertList(page.getRecords()));
        return voPage;
    }

    private List<FraudCaseVO> convertList(List<FraudCase> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Set<Long> ownerIds = new HashSet<>();
        Set<Long> alertIds = new HashSet<>();
        for (FraudCase fraudCase : list) {
            if (fraudCase.getOwnerId() != null) {
                ownerIds.add(fraudCase.getOwnerId());
            }
            if (fraudCase.getAlertId() != null) {
                alertIds.add(fraudCase.getAlertId());
            }
        }
        Map<Long, User> userMap = new HashMap<>();
        if (!ownerIds.isEmpty()) {
            List<User> users = userMapper.selectBatchIds(ownerIds);
            userMap = users.stream().collect(Collectors.toMap(User::getId, item -> item));
        }
        Map<Long, FraudAlert> alertMap = new HashMap<>();
        if (!alertIds.isEmpty()) {
            List<FraudAlert> alerts = alertMapper.selectBatchIds(alertIds);
            alertMap = alerts.stream().collect(Collectors.toMap(FraudAlert::getId, item -> item));
        }
        List<FraudCaseVO> result = new ArrayList<>();
        for (FraudCase fraudCase : list) {
            FraudCaseVO vo = new FraudCaseVO();
            BeanUtils.copyProperties(fraudCase, vo);
            User owner = userMap.get(fraudCase.getOwnerId());
            FraudAlert alert = alertMap.get(fraudCase.getAlertId());
            vo.setOwnerName(owner == null ? "未知用户" : (owner.getNickname() == null || owner.getNickname().trim().isEmpty() ? owner.getUsername() : owner.getNickname()));
            vo.setAlertNo(alert == null ? "预警已删除" : alert.getAlertNo());
            result.add(vo);
        }
        return result;
    }
}
