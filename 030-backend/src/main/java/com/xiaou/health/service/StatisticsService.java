package com.xiaou.health.service;

import com.xiaou.health.entity.Consultation;
import com.xiaou.health.entity.DoctorInfo;
import com.xiaou.health.entity.HealthAlert;
import com.xiaou.health.entity.HealthData;
import com.xiaou.health.entity.User;
import com.xiaou.health.repository.ConsultationRepository;
import com.xiaou.health.repository.DoctorInfoRepository;
import com.xiaou.health.repository.HealthAlertRepository;
import com.xiaou.health.repository.HealthAssessmentRepository;
import com.xiaou.health.repository.HealthDataRepository;
import com.xiaou.health.repository.HealthKnowledgeRepository;
import com.xiaou.health.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StatisticsService {
    private final UserRepository userRepository;
    private final DoctorInfoRepository doctorInfoRepository;
    private final ConsultationRepository consultationRepository;
    private final HealthDataRepository healthDataRepository;
    private final HealthKnowledgeRepository healthKnowledgeRepository;
    private final HealthAlertRepository healthAlertRepository;
    private final HealthAssessmentRepository healthAssessmentRepository;

    public StatisticsService(UserRepository userRepository,
                             DoctorInfoRepository doctorInfoRepository,
                             ConsultationRepository consultationRepository,
                             HealthDataRepository healthDataRepository,
                             HealthKnowledgeRepository healthKnowledgeRepository,
                             HealthAlertRepository healthAlertRepository,
                             HealthAssessmentRepository healthAssessmentRepository) {
        this.userRepository = userRepository;
        this.doctorInfoRepository = doctorInfoRepository;
        this.consultationRepository = consultationRepository;
        this.healthDataRepository = healthDataRepository;
        this.healthKnowledgeRepository = healthKnowledgeRepository;
        this.healthAlertRepository = healthAlertRepository;
        this.healthAssessmentRepository = healthAssessmentRepository;
    }

    public Map<String, Object> getAdminStatistics() {
        List<Consultation> consultations = consultationRepository.findAll();
        List<HealthData> healthDataList = healthDataRepository.findAll();
        List<HealthAlert> alerts = healthAlertRepository.findAll();
        List<DoctorInfo> doctorInfos = doctorInfoRepository.findAll();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalUsers", userRepository.count());
        result.put("totalDoctors", userRepository.countByRole("DOCTOR"));
        result.put("totalPatients", userRepository.countByRole("PATIENT"));
        result.put("totalAdmins", userRepository.countByRole("ADMIN"));
        result.put("verifiedDoctors", doctorInfoRepository.countByVerifyStatus(1));
        result.put("pendingDoctors", doctorInfoRepository.countByVerifyStatus(0));
        result.put("totalConsultations", consultations.size());
        result.put("pendingConsultations", consultations.stream().filter(item -> "PENDING".equals(item.getStatus())).count());
        result.put("answeredConsultations", consultations.stream().filter(item -> "ANSWERED".equals(item.getStatus())).count());
        result.put("ratedConsultations", consultations.stream().filter(item -> item.getRating() != null).count());
        result.put("averageRating", averageRating(consultations));
        result.put("averageConsultationRating", averageRating(consultations));
        result.put("publishedKnowledgeCount", healthKnowledgeRepository.countByStatus(1));
        result.put("totalHealthData", healthDataList.size());
        result.put("totalAssessments", healthAssessmentRepository.count());
        result.put("totalAlerts", alerts.size());
        result.put("unreadAlerts", healthAlertRepository.countByIsRead(0));
        result.put("consultationTrend", buildTrend(consultations, Consultation::getCreateTime));
        result.put("healthDataTrend", buildTrend(healthDataList, item -> item.getMeasureTime() != null ? item.getMeasureTime() : item.getCreateTime()));
        result.put("alertTrend", buildTrend(alerts, HealthAlert::getCreateTime));
        result.put("doctorServiceRanking", buildDoctorRanking(consultations, doctorInfos));
        return result;
    }

    public Map<String, Object> getDoctorStatistics(Long doctorId) {
        List<Consultation> consultations = consultationRepository.findByDoctorIdOrderByCreateTimeDesc(doctorId);
        Map<Long, String> patientNameMap = buildUserNameMap(
                consultations.stream().map(Consultation::getPatientId).distinct().toList()
        );
        DoctorInfo doctorInfo = doctorInfoRepository.findByUserId(doctorId).orElse(null);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalConsultations", consultations.size());
        result.put("pendingConsultations", consultations.stream().filter(item -> "PENDING".equals(item.getStatus())).count());
        result.put("answeredConsultations", consultations.stream().filter(item -> "ANSWERED".equals(item.getStatus())).count());
        result.put("ratedConsultations", consultations.stream().filter(item -> item.getRating() != null).count());
        result.put("averageRating", averageRating(consultations));
        result.put("doctorTitle", doctorInfo != null ? doctorInfo.getTitle() : null);
        result.put("doctorDepartment", doctorInfo != null ? doctorInfo.getDepartment() : null);
        result.put("doctorHospital", doctorInfo != null ? doctorInfo.getHospital() : null);
        result.put("consultationTrend", buildTrend(consultations, Consultation::getCreateTime));
        result.put("recentPendingConsultations", consultations.stream()
                .filter(item -> "PENDING".equals(item.getStatus()))
                .limit(5)
                .map(item -> buildConsultationSummary(item, patientNameMap.get(item.getPatientId())))
                .toList());
        result.put("recentAnsweredConsultations", consultations.stream()
                .filter(item -> "ANSWERED".equals(item.getStatus()))
                .limit(5)
                .map(item -> buildConsultationSummary(item, patientNameMap.get(item.getPatientId())))
                .toList());
        return result;
    }

    private List<Map<String, Object>> buildDoctorRanking(List<Consultation> consultations, List<DoctorInfo> doctorInfos) {
        Map<Long, DoctorInfo> doctorInfoMap = doctorInfos.stream()
                .collect(Collectors.toMap(DoctorInfo::getUserId, Function.identity(), (left, right) -> left));
        Map<Long, String> doctorNameMap = buildUserNameMap(new ArrayList<>(doctorInfoMap.keySet()));

        return consultations.stream()
                .collect(Collectors.groupingBy(Consultation::getDoctorId))
                .entrySet()
                .stream()
                .map(entry -> {
                    Long doctorId = entry.getKey();
                    List<Consultation> doctorConsultations = entry.getValue();
                    DoctorInfo doctorInfo = doctorInfoMap.get(doctorId);

                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("doctorId", doctorId);
                    item.put("doctorName", doctorNameMap.getOrDefault(doctorId, "医生#" + doctorId));
                    item.put("title", doctorInfo != null ? doctorInfo.getTitle() : null);
                    item.put("department", doctorInfo != null ? doctorInfo.getDepartment() : null);
                    item.put("hospital", doctorInfo != null ? doctorInfo.getHospital() : null);
                    item.put("consultationCount", doctorConsultations.size());
                    item.put("totalConsultations", doctorConsultations.size());
                    item.put("pendingCount", doctorConsultations.stream().filter(value -> "PENDING".equals(value.getStatus())).count());
                    item.put("answeredCount", doctorConsultations.stream().filter(value -> "ANSWERED".equals(value.getStatus())).count());
                    item.put("answeredConsultations", doctorConsultations.stream().filter(value -> "ANSWERED".equals(value.getStatus())).count());
                    item.put("averageRating", averageRating(doctorConsultations));
                    return item;
                })
                .sorted((left, right) -> {
                    long countCompare = Long.compare(
                            ((Number) right.get("consultationCount")).longValue(),
                            ((Number) left.get("consultationCount")).longValue()
                    );
                    if (countCompare != 0) {
                        return (int) countCompare;
                    }
                    return Long.compare(
                            ((Number) right.get("answeredCount")).longValue(),
                            ((Number) left.get("answeredCount")).longValue()
                    );
                })
                .limit(5)
                .toList();
    }

    private Map<String, Object> buildConsultationSummary(Consultation consultation, String patientName) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("id", consultation.getId());
        item.put("title", consultation.getTitle());
        item.put("status", consultation.getStatus());
        item.put("patientName", patientName);
        item.put("question", consultation.getQuestion());
        item.put("rating", consultation.getRating());
        item.put("createTime", consultation.getCreateTime());
        item.put("answeredTime", consultation.getAnsweredTime());
        return item;
    }

    private Map<Long, String> buildUserNameMap(List<Long> userIds) {
        return userRepository.findAllById(userIds).stream()
                .collect(Collectors.toMap(User::getId, user -> {
                    if (user.getRealName() != null && !user.getRealName().isBlank()) {
                        return user.getRealName();
                    }
                    return user.getUsername();
                }, (left, right) -> left));
    }

    private double averageRating(List<Consultation> consultations) {
        List<Integer> ratings = consultations.stream()
                .map(Consultation::getRating)
                .filter(value -> value != null && value > 0)
                .toList();
        if (ratings.isEmpty()) {
            return 0.0D;
        }
        BigDecimal average = BigDecimal.valueOf(
                ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0D)
        ).setScale(1, RoundingMode.HALF_UP);
        return average.doubleValue();
    }

    private <T> List<Map<String, Object>> buildTrend(List<T> source, Function<T, LocalDateTime> timeExtractor) {
        LocalDate today = LocalDate.now();
        Map<LocalDate, Long> countMap = new HashMap<>();

        for (T item : source) {
            LocalDateTime time = timeExtractor.apply(item);
            if (time == null) {
                continue;
            }
            LocalDate date = time.toLocalDate();
            if (!date.isBefore(today.minusDays(6)) && !date.isAfter(today)) {
                countMap.merge(date, 1L, Long::sum);
            }
        }

        List<Map<String, Object>> trend = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            Map<String, Object> point = new LinkedHashMap<>();
            long count = countMap.getOrDefault(date, 0L);
            point.put("date", date.toString());
            point.put("count", count);
            point.put("value", count);
            trend.add(point);
        }
        return trend;
    }
}
