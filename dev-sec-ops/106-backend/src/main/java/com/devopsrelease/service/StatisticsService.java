package com.devopsrelease.service;

import com.devopsrelease.mapper.ApplicationServiceMapper;
import com.devopsrelease.mapper.DeployTaskMapper;
import com.devopsrelease.mapper.ReleaseOrderMapper;
import com.devopsrelease.mapper.RollbackRecordMapper;
import com.devopsrelease.mapper.VersionArtifactMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ApplicationServiceMapper serviceMapper;
    private final ReleaseOrderMapper orderMapper;
    private final DeployTaskMapper taskMapper;
    private final RollbackRecordMapper rollbackMapper;
    private final VersionArtifactMapper artifactMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("serviceCount", serviceMapper.countAll());
        data.put("orderCount", orderMapper.countAll());
        data.put("taskCount", taskMapper.countAll());
        data.put("rollbackCount", rollbackMapper.countAll());
        data.put("artifactCount", artifactMapper.countAll());
        data.put("releaseTrend", Arrays.asList(4, 6, 5, 8, 7, 9, 11));
        data.put("statusPie", Arrays.asList(map("成功", 18), map("审批中", 6), map("失败", 3), map("回滚", 2)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
