package com.certtraining.clerk;

import com.certtraining.mapper.TrainingCourseMapper;
import com.certtraining.mapper.TraineeProfileMapper;
import com.certtraining.mapper.InstructorProfileMapper;
import com.certtraining.mapper.CourseEnrollmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final TrainingCourseMapper trainingCourseMapper;
    private final TraineeProfileMapper traineeProfileMapper;
    private final InstructorProfileMapper instructorProfileMapper;
    private final CourseEnrollmentMapper courseEnrollmentMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("courseCount", trainingCourseMapper.countAll());
        data.put("traineeCount", traineeProfileMapper.countAll());
        data.put("instructorCount", instructorProfileMapper.countAll());
        data.put("enrollmentCount", courseEnrollmentMapper.countAll());
        data.put("trend", Arrays.asList(118, 142, 168, 201, 236, 258, 291));
        data.put("pie", Arrays.asList(map("学习中", 64), map("待考试", 31), map("已发证", 42), map("待续证", 17)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
