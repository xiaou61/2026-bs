package com.innovationclass.clerk;

import com.innovationclass.mapper.InnovationProgramMapper;
import com.innovationclass.mapper.StudentProfileMapper;
import com.innovationclass.mapper.MentorProfileMapper;
import com.innovationclass.mapper.SelectionNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final InnovationProgramMapper innovationProgramMapper;
    private final StudentProfileMapper studentProfileMapper;
    private final MentorProfileMapper mentorProfileMapper;
    private final SelectionNoticeMapper selectionNoticeMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("programCount", innovationProgramMapper.countAll());
        data.put("studentCount", studentProfileMapper.countAll());
        data.put("mentorCount", mentorProfileMapper.countAll());
        data.put("noticeCount", selectionNoticeMapper.countAll());
        data.put("trend", Arrays.asList(31, 46, 58, 72, 86, 101, 119));
        data.put("pie", Arrays.asList(map("报名中", 33), map("评审中", 27), map("面试中", 18), map("已匹配", 41), map("跟踪中", 36)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
