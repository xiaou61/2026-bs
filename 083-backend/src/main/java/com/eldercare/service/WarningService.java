package com.eldercare.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldercare.entity.AbnormalWarning;
import com.eldercare.mapper.AbnormalWarningMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WarningService {

    @Autowired
    private AbnormalWarningMapper abnormalWarningMapper;

    public Page<AbnormalWarning> page(int pageNum, int pageSize, Long elderId, String warningLevel, Integer status) {
        Page<AbnormalWarning> page = new Page<>(pageNum, pageSize);
        QueryWrapper<AbnormalWarning> wrapper = new QueryWrapper<>();
        if (elderId != null) {
            wrapper.eq("elder_id", elderId);
        }
        if (warningLevel != null && warningLevel.trim().length() > 0) {
            wrapper.eq("warning_level", warningLevel);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return abnormalWarningMapper.selectPage(page, wrapper);
    }

    public void updateStatus(Long id, Integer status) {
        AbnormalWarning warning = new AbnormalWarning();
        warning.setId(id);
        warning.setStatus(status);
        abnormalWarningMapper.updateById(warning);
    }

    public void delete(Long id) {
        abnormalWarningMapper.deleteById(id);
    }

    public long countAll() {
        return abnormalWarningMapper.selectCount(new QueryWrapper<AbnormalWarning>());
    }

    public List<Map<String, Object>> distribution() {
        QueryWrapper<AbnormalWarning> wrapper = new QueryWrapper<>();
        wrapper.select("warning_level AS name", "COUNT(*) AS value").groupBy("warning_level");
        return abnormalWarningMapper.selectMaps(wrapper);
    }
}
