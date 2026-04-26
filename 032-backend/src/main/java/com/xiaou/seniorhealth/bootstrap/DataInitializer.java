package com.xiaou.seniorhealth.bootstrap;

import com.xiaou.seniorhealth.domain.Appointment;
import com.xiaou.seniorhealth.domain.Elder;
import com.xiaou.seniorhealth.domain.FollowUp;
import com.xiaou.seniorhealth.domain.Measurement;
import com.xiaou.seniorhealth.domain.Notification;
import com.xiaou.seniorhealth.domain.SysUser;
import com.xiaou.seniorhealth.repository.AppointmentRepository;
import com.xiaou.seniorhealth.repository.ElderRepository;
import com.xiaou.seniorhealth.repository.FollowUpRepository;
import com.xiaou.seniorhealth.repository.MeasurementRepository;
import com.xiaou.seniorhealth.repository.NotificationRepository;
import com.xiaou.seniorhealth.repository.SysUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    private final SysUserRepository userRepository;
    private final ElderRepository elderRepository;
    private final MeasurementRepository measurementRepository;
    private final AppointmentRepository appointmentRepository;
    private final FollowUpRepository followUpRepository;
    private final NotificationRepository notificationRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            SysUserRepository userRepository,
            ElderRepository elderRepository,
            MeasurementRepository measurementRepository,
            AppointmentRepository appointmentRepository,
            FollowUpRepository followUpRepository,
            NotificationRepository notificationRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.elderRepository = elderRepository;
        this.measurementRepository = measurementRepository;
        this.appointmentRepository = appointmentRepository;
        this.followUpRepository = followUpRepository;
        this.notificationRepository = notificationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        SysUser admin = seedUser("admin", "admin123", "ADMIN");
        SysUser doctor = seedUser("doctor1", "doctor123", "DOCTOR");
        SysUser elderUser = seedUser("elder1", "elder123", "ELDER");
        Elder elder = seedElder(elderUser);
        seedMeasurements(elder);
        seedAppointment(elder, doctor);
        seedFollowUp(elder, doctor);
        seedNotification(elderUser.getId(), "健康提醒", "今日请按时记录血压和体重。");
        seedNotification(doctor.getId(), "服务提醒", "您有一条待处理的老人随访计划。");
        seedNotification(admin.getId(), "系统提醒", "默认演示数据已初始化完成。");
    }

    private SysUser seedUser(String username, String rawPassword, String role) {
        return userRepository.findByUsername(username).orElseGet(() -> {
            SysUser user = new SysUser();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setRole(role);
            user.setStatus(1);
            user.setCreatedAt(LocalDateTime.now());
            return userRepository.save(user);
        });
    }

    private Elder seedElder(SysUser elderUser) {
        return elderRepository.findByUserId(elderUser.getId()).orElseGet(() -> {
            Elder elder = new Elder();
            elder.setUserId(elderUser.getId());
            elder.setName("李奶奶");
            elder.setGender("FEMALE");
            elder.setBirthDate(LocalDate.of(1952, 9, 1));
            elder.setPhone("13888880001");
            elder.setAddress("社区二号楼");
            elder.setEmergencyContact("李叔叔 13888880002");
            elder.setCreatedAt(LocalDateTime.now());
            return elderRepository.save(elder);
        });
    }

    private void seedMeasurements(Elder elder) {
        if (!measurementRepository.findLatestByElder(elder.getId(), 1).isEmpty()) {
            return;
        }
        saveMeasurement(elder.getId(), "WEIGHT", 67.5, null, "kg", LocalDateTime.now().minusDays(2));
        saveMeasurement(elder.getId(), "HEIGHT", 160.0, null, "cm", LocalDateTime.now().minusDays(2));
        saveMeasurement(elder.getId(), "BP", 146.0, 92.0, "mmHg", LocalDateTime.now().minusHours(6));
    }

    private void saveMeasurement(Long elderId, String type, Double value1, Double value2, String unit, LocalDateTime measuredAt) {
        Measurement measurement = new Measurement();
        measurement.setElderId(elderId);
        measurement.setType(type);
        measurement.setValue1(value1);
        measurement.setValue2(value2);
        measurement.setUnit(unit);
        measurement.setMeasuredAt(measuredAt);
        measurement.setCreatedAt(LocalDateTime.now());
        measurementRepository.save(measurement);
    }

    private void seedAppointment(Elder elder, SysUser doctor) {
        if (!appointmentRepository.findByElder(elder.getId()).isEmpty()) {
            return;
        }
        Appointment appointment = new Appointment();
        appointment.setElderId(elder.getId());
        appointment.setDoctorUserId(doctor.getId());
        appointment.setStartTime(LocalDateTime.now().plusDays(1).withMinute(30).withSecond(0).withNano(0));
        appointment.setReason("高血压复诊");
        appointment.setStatus("SCHEDULED");
        appointment.setCreatedAt(LocalDateTime.now());
        appointmentRepository.save(appointment);
    }

    private void seedFollowUp(Elder elder, SysUser doctor) {
        if (!followUpRepository.findByElder(elder.getId()).isEmpty()) {
            return;
        }
        FollowUp followUp = new FollowUp();
        followUp.setElderId(elder.getId());
        followUp.setDoctorUserId(doctor.getId());
        followUp.setType("TEL");
        followUp.setDueDate(LocalDate.now().plusDays(3));
        followUp.setNote("复测血压并记录晨起心率");
        followUp.setStatus("PENDING");
        followUp.setCreatedAt(LocalDateTime.now());
        followUpRepository.save(followUp);
    }

    private void seedNotification(Long userId, String title, String content) {
        if (!notificationRepository.findLatestByUser(userId, 1).isEmpty()) {
            return;
        }
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setTitle(title);
        notification.setContent(content);
        notification.setStatus("UNREAD");
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }
}
