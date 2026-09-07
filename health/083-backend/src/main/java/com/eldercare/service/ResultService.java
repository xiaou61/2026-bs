package com.eldercare.service;

import com.eldercare.common.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldercare.entity.AbnormalWarning;
import com.eldercare.entity.CheckAppointment;
import com.eldercare.entity.CheckItem;
import com.eldercare.entity.CheckResult;
import com.eldercare.mapper.AbnormalWarningMapper;
import com.eldercare.mapper.CheckAppointmentMapper;
import com.eldercare.mapper.CheckItemMapper;
import com.eldercare.mapper.CheckResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ResultService {

    @Autowired
    private CheckResultMapper checkResultMapper;

    @Autowired
    private CheckItemMapper checkItemMapper;

    @Autowired
    private AbnormalWarningMapper abnormalWarningMapper;

    @Autowired
    private CheckAppointmentMapper checkAppointmentMapper;

    public Page<CheckResult> page(int pageNum, int pageSize, Long appointmentId, Long elderId, Integer abnormalFlag) {
        Page<CheckResult> page = new Page<>(pageNum, pageSize);
        QueryWrapper<CheckResult> wrapper = new QueryWrapper<>();
        if (appointmentId != null) {
            wrapper.eq("appointment_id", appointmentId);
        }
        if (elderId != null) {
            wrapper.eq("elder_id", elderId);
        }
        if (abnormalFlag != null) {
            wrapper.eq("abnormal_flag", abnormalFlag);
        }
        wrapper.orderByDesc("create_time");
        return checkResultMapper.selectPage(page, wrapper);
    }

    public void add(CheckResult checkResult, Long userId) {
        CheckAppointment appointment = requireAppointment(checkResult.getAppointmentId());
        if (checkResult.getCheckTime() == null) {
            checkResult.setCheckTime(LocalDateTime.now());
        }
        checkResult.setElderId(appointment.getElderId());
        checkResult.setDoctorId(userId);
        boolean abnormal = isAbnormal(checkResult);
        checkResult.setAbnormalFlag(abnormal ? 1 : 0);
        checkResultMapper.insert(checkResult);
        if (abnormal) {
            createWarning(checkResult);
        }
        CheckAppointment updateAppointment = new CheckAppointment();
        updateAppointment.setId(checkResult.getAppointmentId());
        updateAppointment.setStatus(2);
        checkAppointmentMapper.updateById(updateAppointment);
    }

    public void update(CheckResult checkResult, Long userId) {
        CheckAppointment appointment = requireAppointment(checkResult.getAppointmentId());
        if (checkResult.getCheckTime() == null) {
            checkResult.setCheckTime(LocalDateTime.now());
        }
        checkResult.setElderId(appointment.getElderId());
        checkResult.setDoctorId(userId);
        boolean abnormal = isAbnormal(checkResult);
        checkResult.setAbnormalFlag(abnormal ? 1 : 0);
        checkResultMapper.updateById(checkResult);
        if (abnormal) {
            createWarning(checkResult);
        }
    }

    public void delete(Long id) {
        checkResultMapper.deleteById(id);
    }

    public CheckResult getById(Long id) {
        CheckResult checkResult = checkResultMapper.selectById(id);
        if (checkResult == null) {
            throw new BusinessException(404, "体检结果不存在");
        }
        return checkResult;
    }

    private boolean isAbnormal(CheckResult checkResult) {
        CheckItem checkItem = checkItemMapper.selectById(checkResult.getItemId());
        if (checkItem == null) {
            return false;
        }
        try {
            BigDecimal value = new BigDecimal(checkResult.getItemValue());
            if (checkItem.getLowLimit() != null && value.compareTo(checkItem.getLowLimit()) < 0) {
                return true;
            }
            if (checkItem.getHighLimit() != null && value.compareTo(checkItem.getHighLimit()) > 0) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private void createWarning(CheckResult checkResult) {
        QueryWrapper<AbnormalWarning> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("result_id", checkResult.getId());
        Long count = abnormalWarningMapper.selectCount(queryWrapper);
        if (count != null && count > 0) {
            return;
        }
        CheckItem checkItem = checkItemMapper.selectById(checkResult.getItemId());
        AbnormalWarning warning = new AbnormalWarning();
        warning.setResultId(checkResult.getId());
        warning.setElderId(checkResult.getElderId());
        warning.setItemId(checkResult.getItemId());
        warning.setWarningLevel(checkItem == null ? "medium" : checkItem.getWarningLevel());
        warning.setWarningContent((checkItem == null ? "体检项目" : checkItem.getItemName()) + "指标异常: " + checkResult.getItemValue());
        warning.setStatus(0);
        abnormalWarningMapper.insert(warning);
    }

    private CheckAppointment requireAppointment(Long appointmentId) {
        CheckAppointment appointment = checkAppointmentMapper.selectById(appointmentId);
        if (appointment == null) {
            throw new BusinessException(404, "预约记录不存在");
        }
        return appointment;
    }
}
