package com.disaster.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.disaster.entity.Disaster;
import com.disaster.mapper.DisasterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DisasterService {

    @Autowired
    private DisasterMapper disasterMapper;

    public void report(Disaster disaster, Long reporterId) {
        disaster.setReporterId(reporterId);
        disaster.setReportTime(LocalDateTime.now());
        disaster.setStatus(0);
        disasterMapper.insert(disaster);
    }

    public Page<Disaster> page(int pageNum, int pageSize, String type, Integer status, Integer level, String keyword) {
        LambdaQueryWrapper<Disaster> wrapper = new LambdaQueryWrapper<>();
        if (type != null && !type.isEmpty()) {
            wrapper.eq(Disaster::getType, type);
        }
        if (status != null) {
            wrapper.eq(Disaster::getStatus, status);
        }
        if (level != null) {
            wrapper.eq(Disaster::getLevel, level);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(Disaster::getTitle, keyword)
                    .or().like(Disaster::getAddress, keyword));
        }
        wrapper.orderByDesc(Disaster::getCreateTime);
        return disasterMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public Disaster getById(Long id) {
        return disasterMapper.selectById(id);
    }

    public void update(Disaster disaster) {
        disasterMapper.updateById(disaster);
    }

    public void updateStatus(Long id, Integer status) {
        Disaster disaster = new Disaster();
        disaster.setId(id);
        disaster.setStatus(status);
        disasterMapper.updateById(disaster);
    }

    public void delete(Long id) {
        disasterMapper.deleteById(id);
    }

    public List<Disaster> listAll() {
        return disasterMapper.selectList(new LambdaQueryWrapper<Disaster>()
                .orderByDesc(Disaster::getCreateTime));
    }

    public Map<String, Object> stats() {
        Map<String, Object> result = new HashMap<>();
        result.put("total", disasterMapper.selectCount(null));
        result.put("pending", disasterMapper.selectCount(new LambdaQueryWrapper<Disaster>().eq(Disaster::getStatus, 0)));
        result.put("processing", disasterMapper.selectCount(new LambdaQueryWrapper<Disaster>().eq(Disaster::getStatus, 1)));
        result.put("completed", disasterMapper.selectCount(new LambdaQueryWrapper<Disaster>().eq(Disaster::getStatus, 2)));
        return result;
    }
}
