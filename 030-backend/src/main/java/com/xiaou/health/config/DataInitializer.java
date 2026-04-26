package com.xiaou.health.config;

import com.xiaou.health.common.Constants;
import com.xiaou.health.entity.DoctorInfo;
import com.xiaou.health.entity.HealthKnowledge;
import com.xiaou.health.entity.PatientInfo;
import com.xiaou.health.entity.User;
import com.xiaou.health.repository.DoctorInfoRepository;
import com.xiaou.health.repository.HealthKnowledgeRepository;
import com.xiaou.health.repository.PatientInfoRepository;
import com.xiaou.health.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PatientInfoRepository patientInfoRepository;
    private final DoctorInfoRepository doctorInfoRepository;
    private final HealthKnowledgeRepository healthKnowledgeRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public DataInitializer(UserRepository userRepository,
                           PatientInfoRepository patientInfoRepository,
                           DoctorInfoRepository doctorInfoRepository,
                           HealthKnowledgeRepository healthKnowledgeRepository) {
        this.userRepository = userRepository;
        this.patientInfoRepository = patientInfoRepository;
        this.doctorInfoRepository = doctorInfoRepository;
        this.healthKnowledgeRepository = healthKnowledgeRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        User admin = createUserIfAbsent("admin", "admin123", Constants.ROLE_ADMIN, "平台管理员", "admin@health.local", "13800000000");
        User patient = createUserIfAbsent("patient", "patient123", Constants.ROLE_PATIENT, "默认患者", "patient@health.local", "13800000001");
        User doctor = createUserIfAbsent("doctor", "doctor123", Constants.ROLE_DOCTOR, "默认医生", "doctor@health.local", "13800000002");

        if (patientInfoRepository.findByUserId(patient.getId()).isEmpty()) {
            PatientInfo patientInfo = new PatientInfo();
            patientInfo.setUserId(patient.getId());
            patientInfo.setAddress("社区健康中心示例地址");
            patientInfo.setLifestyle("规律作息，每周适量运动");
            patientInfo.setSmokingStatus("NEVER");
            patientInfo.setDrinkingStatus("OCCASIONAL");
            patientInfoRepository.save(patientInfo);
        }

        if (doctorInfoRepository.findByUserId(doctor.getId()).isEmpty()) {
            DoctorInfo doctorInfo = new DoctorInfo();
            doctorInfo.setUserId(doctor.getId());
            doctorInfo.setTitle("主治医师");
            doctorInfo.setHospital("社区健康中心");
            doctorInfo.setDepartment("全科医学");
            doctorInfo.setSpecialty("慢病管理");
            doctorInfo.setIntroduction("负责社区慢病随访、健康评估与线上问诊。");
            doctorInfo.setLicenseNumber("DOC-2026-0001");
            doctorInfo.setYearsOfExperience(8);
            doctorInfo.setConsultationFee(20);
            doctorInfo.setVerifyStatus(1);
            doctorInfoRepository.save(doctorInfo);
        }

        if (healthKnowledgeRepository.count() == 0) {
            healthKnowledgeRepository.save(createKnowledge(
                    admin.getId(),
                    "慢病管理",
                    "高血压日常管理建议",
                    "围绕血压监测、饮食控制和规律复诊给出家庭场景下的基础建议。",
                    "1. 每天固定时段测量并记录血压。\n2. 控制盐分摄入，优先清淡饮食。\n3. 每周保持适量有氧运动。\n4. 按医嘱服药，不自行停药。\n5. 出现头晕、胸闷时及时联系医生。"
            ));
            healthKnowledgeRepository.save(createKnowledge(
                    admin.getId(),
                    "运动康复",
                    "社区居民科学步行指南",
                    "针对久坐、体重管理和日常心肺锻炼，给出循序渐进的步行方案。",
                    "1. 从每天 20 分钟快走开始。\n2. 运动前后做好热身和拉伸。\n3. 心率过快或明显不适时降低强度。\n4. 配合饮水和睡眠管理，形成长期习惯。"
            ));
            healthKnowledgeRepository.save(createKnowledge(
                    admin.getId(),
                    "营养膳食",
                    "糖代谢异常人群饮食提醒",
                    "帮助居民理解控糖饮食中的主食选择、加餐节奏和复查重点。",
                    "1. 主食优先粗细搭配，避免高糖饮料。\n2. 少量多餐，避免暴饮暴食。\n3. 定期监测空腹和餐后血糖。\n4. 饮食调整与医生建议同步执行。"
            ));
        }
    }

    private User createUserIfAbsent(String username,
                                    String rawPassword,
                                    String role,
                                    String realName,
                                    String email,
                                    String phone) {
        return userRepository.findByUsername(username).orElseGet(() -> {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setRole(role);
            user.setRealName(realName);
            user.setEmail(email);
            user.setPhone(phone);
            user.setStatus(1);
            user.setVerified(Constants.ROLE_ADMIN.equals(role) ? 1 : 0);
            return userRepository.save(user);
        });
    }

    private HealthKnowledge createKnowledge(Long authorId,
                                            String category,
                                            String title,
                                            String summary,
                                            String content) {
        HealthKnowledge knowledge = new HealthKnowledge();
        knowledge.setAuthorId(authorId);
        knowledge.setCategory(category);
        knowledge.setTitle(title);
        knowledge.setSummary(summary);
        knowledge.setContent(content);
        knowledge.setStatus(1);
        knowledge.setViewCount(0);
        knowledge.setLikeCount(0);
        return knowledge;
    }
}
