package com.grademployment.clerk;

import com.grademployment.mapper.CollegeMajorMapper;
import com.grademployment.mapper.GraduateProfileMapper;
import com.grademployment.mapper.EmployerProfileMapper;
import com.grademployment.mapper.JobPositionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final CollegeMajorMapper collegeMajorMapper;
    private final GraduateProfileMapper graduateProfileMapper;
    private final EmployerProfileMapper employerProfileMapper;
    private final JobPositionMapper jobPositionMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("majorCount", collegeMajorMapper.countAll());
        data.put("graduateCount", graduateProfileMapper.countAll());
        data.put("employerCount", employerProfileMapper.countAll());
        data.put("jobCount", jobPositionMapper.countAll());
        data.put("trend", Arrays.asList(65, 82, 97, 114, 132, 149, 168));
        data.put("pie", Arrays.asList(map("待填报", 28), map("审核中", 36), map("帮扶中", 22), map("已就业", 74)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
