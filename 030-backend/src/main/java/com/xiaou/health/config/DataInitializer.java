package com.xiaou.health.config;

import com.xiaou.health.common.Constants;
import com.xiaou.health.entity.DoctorInfo;
import com.xiaou.health.entity.PatientInfo;
import com.xiaou.health.entity.User;
import com.xiaou.health.repository.DoctorInfoRepository;
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
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public DataInitializer(UserRepository userRepository,
                           PatientInfoRepository patientInfoRepository,
                           DoctorInfoRepository doctorInfoRepository) {
        this.userRepository = userRepository;
        this.patientInfoRepository = patientInfoRepository;
        this.doctorInfoRepository = doctorInfoRepository;
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
}
