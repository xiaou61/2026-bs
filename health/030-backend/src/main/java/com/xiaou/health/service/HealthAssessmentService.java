package com.xiaou.health.service;

import com.xiaou.health.entity.HealthAlert;
import com.xiaou.health.entity.HealthAssessment;
import com.xiaou.health.entity.HealthData;
import com.xiaou.health.repository.HealthAlertRepository;
import com.xiaou.health.repository.HealthAssessmentRepository;
import com.xiaou.health.repository.HealthDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class HealthAssessmentService {
    private final HealthAssessmentRepository healthAssessmentRepository;
    private final HealthAlertRepository healthAlertRepository;
    private final HealthDataRepository healthDataRepository;

    public HealthAssessmentService(HealthAssessmentRepository healthAssessmentRepository,
                                   HealthAlertRepository healthAlertRepository,
                                   HealthDataRepository healthDataRepository) {
        this.healthAssessmentRepository = healthAssessmentRepository;
        this.healthAlertRepository = healthAlertRepository;
        this.healthDataRepository = healthDataRepository;
    }

    @Transactional
    public HealthAssessment generateAssessment(Long patientId) {
        List<HealthData> healthDataList = healthDataRepository.findByPatientIdOrderByMeasureTimeDesc(patientId);
        Map<String, HealthData> latestDataMap = new LinkedHashMap<>();
        for (HealthData healthData : healthDataList) {
            String normalizedType = normalizeDataType(healthData.getDataType());
            latestDataMap.putIfAbsent(normalizedType, healthData);
        }

        List<String> riskFactors = new ArrayList<>();
        List<String> suggestions = new ArrayList<>();
        int score = 100;

        HealthAssessment assessment = new HealthAssessment();
        assessment.setPatientId(patientId);
        assessment.setAssessmentDate(LocalDateTime.now());

        HealthData bloodPressure = latestDataMap.get("BLOOD_PRESSURE");
        if (bloodPressure != null && bloodPressure.getValue() != null) {
            MetricEvaluation evaluation = evaluateBloodPressure(bloodPressure.getValue());
            assessment.setBloodPressureLevel(evaluation.level());
            score -= evaluation.scorePenalty();
            if (evaluation.abnormal()) {
                riskFactors.add("血压异常：" + evaluation.description());
                suggestions.add("建议连续监测血压，减少高盐饮食，并根据情况联系医生复诊。");
                createAlertIfNecessary(patientId, "BLOOD_PRESSURE", evaluation.alertLevel(),
                        "血压异常预警", "最近一次血压记录为 " + bloodPressure.getValue().stripTrailingZeros().toPlainString()
                                + "，判定为" + evaluation.description() + "。");
            }
        }

        HealthData bloodSugar = latestDataMap.get("BLOOD_SUGAR");
        if (bloodSugar != null && bloodSugar.getValue() != null) {
            MetricEvaluation evaluation = evaluateBloodSugar(bloodSugar.getValue());
            assessment.setBloodSugarLevel(evaluation.level());
            score -= evaluation.scorePenalty();
            if (evaluation.abnormal()) {
                riskFactors.add("血糖异常：" + evaluation.description());
                suggestions.add("建议控制精制糖摄入，保持规律进餐，并复测空腹或餐后血糖。");
                createAlertIfNecessary(patientId, "BLOOD_SUGAR", evaluation.alertLevel(),
                        "血糖异常预警", "最近一次血糖记录为 " + bloodSugar.getValue().stripTrailingZeros().toPlainString()
                                + "，判定为" + evaluation.description() + "。");
            }
        }

        HealthData heartRate = latestDataMap.get("HEART_RATE");
        if (heartRate != null && heartRate.getValue() != null) {
            MetricEvaluation evaluation = evaluateHeartRate(heartRate.getValue());
            score -= evaluation.scorePenalty();
            if (evaluation.abnormal()) {
                riskFactors.add("心率异常：" + evaluation.description());
                suggestions.add("建议休息后再次测量心率，若持续异常请及时咨询医生。");
                createAlertIfNecessary(patientId, "HEART_RATE", evaluation.alertLevel(),
                        "心率异常预警", "最近一次心率记录为 " + heartRate.getValue().stripTrailingZeros().toPlainString()
                                + "，判定为" + evaluation.description() + "。");
            }
        }

        HealthData temperature = latestDataMap.get("BODY_TEMPERATURE");
        if (temperature != null && temperature.getValue() != null) {
            MetricEvaluation evaluation = evaluateBodyTemperature(temperature.getValue());
            score -= evaluation.scorePenalty();
            if (evaluation.abnormal()) {
                riskFactors.add("体温异常：" + evaluation.description());
                suggestions.add("建议补充水分并关注是否伴随咳嗽、头痛等症状，必要时及时就医。");
                createAlertIfNecessary(patientId, "BODY_TEMPERATURE", evaluation.alertLevel(),
                        "体温异常预警", "最近一次体温记录为 " + temperature.getValue().stripTrailingZeros().toPlainString()
                                + "，判定为" + evaluation.description() + "。");
            }
        }

        HealthData weight = latestDataMap.get("WEIGHT");
        if (weight != null && weight.getValue() != null) {
            MetricEvaluation evaluation = evaluateWeight(weight.getValue());
            score -= evaluation.scorePenalty();
            if (evaluation.abnormal()) {
                riskFactors.add("体重异常：" + evaluation.description());
                suggestions.add("建议结合饮食和运动记录持续观察体重变化，必要时完善身高信息后进行 BMI 评估。");
                createAlertIfNecessary(patientId, "WEIGHT", evaluation.alertLevel(),
                        "体重波动提醒", "最近一次体重记录为 " + weight.getValue().stripTrailingZeros().toPlainString()
                                + "kg，判定为" + evaluation.description() + "。");
            }
            assessment.setBmi(null);
            assessment.setBmiLevel("缺少身高数据，暂无法计算 BMI");
        } else {
            assessment.setBmi(null);
            assessment.setBmiLevel("暂无体重或身高数据");
        }

        if (latestDataMap.isEmpty()) {
            score = 85;
            riskFactors.add("暂无足够健康数据，无法进行完整风险评估。");
            suggestions.add("建议先录入血压、血糖、体重、心率或体温等基础健康数据。");
        } else if (riskFactors.isEmpty()) {
            suggestions.add("当前主要指标整体稳定，建议保持规律作息、饮食和运动习惯。");
        }

        assessment.setHealthScore(BigDecimal.valueOf(Math.max(score, 40)));
        assessment.setRiskFactors(String.join("\n", riskFactors));
        assessment.setSuggestions(String.join("\n", suggestions));
        return healthAssessmentRepository.save(assessment);
    }

    @Transactional(readOnly = true)
    public HealthAssessment getLatestAssessment(Long patientId) {
        return healthAssessmentRepository.findTopByPatientIdOrderByAssessmentDateDesc(patientId)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<HealthAssessment> getAssessmentHistory(Long patientId) {
        return healthAssessmentRepository.findByPatientIdOrderByAssessmentDateDesc(patientId);
    }

    @Transactional(readOnly = true)
    public List<HealthAlert> getAlerts(Long patientId, Integer isRead) {
        if (isRead == null) {
            return healthAlertRepository.findByPatientIdOrderByCreateTimeDesc(patientId);
        }
        return healthAlertRepository.findByPatientIdAndIsRead(patientId, isRead);
    }

    @Transactional(readOnly = true)
    public long countUnreadAlerts(Long patientId) {
        return healthAlertRepository.countByPatientIdAndIsRead(patientId, 0);
    }

    @Transactional
    public HealthAlert markAlertAsRead(Long patientId, Long alertId) {
        HealthAlert alert = healthAlertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("预警消息不存在"));
        if (!alert.getPatientId().equals(patientId)) {
            throw new RuntimeException("无权操作该预警消息");
        }
        alert.setIsRead(1);
        return healthAlertRepository.save(alert);
    }

    private void createAlertIfNecessary(Long patientId, String alertType, String level, String title, String content) {
        HealthAlert latestAlert = healthAlertRepository.findTopByPatientIdAndAlertTypeOrderByCreateTimeDesc(patientId, alertType)
                .orElse(null);
        if (latestAlert != null
                && latestAlert.getContent() != null
                && latestAlert.getContent().equals(content)
                && latestAlert.getCreateTime() != null
                && Duration.between(latestAlert.getCreateTime(), LocalDateTime.now()).toHours() < 24) {
            return;
        }

        HealthAlert alert = new HealthAlert();
        alert.setPatientId(patientId);
        alert.setAlertType(alertType);
        alert.setLevel(level);
        alert.setTitle(title);
        alert.setContent(content);
        alert.setIsRead(0);
        healthAlertRepository.save(alert);
    }

    private String normalizeDataType(String dataType) {
        if (dataType == null) {
            return "";
        }
        String normalized = dataType.trim().replace('-', '_').replace(' ', '_').toUpperCase(Locale.ROOT);
        return switch (normalized) {
            case "血压", "BLOODPRESSURE", "BLOOD_PRESSURE", "SYSTOLIC_PRESSURE" -> "BLOOD_PRESSURE";
            case "血糖", "BLOODSUGAR", "BLOOD_SUGAR", "GLUCOSE" -> "BLOOD_SUGAR";
            case "体重", "WEIGHT" -> "WEIGHT";
            case "心率", "HEARTRATE", "HEART_RATE", "PULSE" -> "HEART_RATE";
            case "体温", "BODYTEMPERATURE", "BODY_TEMPERATURE", "TEMPERATURE" -> "BODY_TEMPERATURE";
            default -> normalized;
        };
    }

    private MetricEvaluation evaluateBloodPressure(BigDecimal value) {
        if (value.compareTo(new BigDecimal("160")) >= 0) {
            return new MetricEvaluation("危险偏高", "HIGH", true, 25, "HIGH");
        }
        if (value.compareTo(new BigDecimal("140")) >= 0) {
            return new MetricEvaluation("偏高", "ELEVATED", true, 15, "MEDIUM");
        }
        if (value.compareTo(new BigDecimal("90")) < 0) {
            return new MetricEvaluation("偏低", "LOW", true, 10, "MEDIUM");
        }
        return new MetricEvaluation("正常", "NORMAL", false, 0, "LOW");
    }

    private MetricEvaluation evaluateBloodSugar(BigDecimal value) {
        if (value.compareTo(new BigDecimal("11")) >= 0) {
            return new MetricEvaluation("明显偏高", "HIGH", true, 25, "HIGH");
        }
        if (value.compareTo(new BigDecimal("7")) >= 0) {
            return new MetricEvaluation("偏高", "ELEVATED", true, 15, "MEDIUM");
        }
        if (value.compareTo(new BigDecimal("3.9")) < 0) {
            return new MetricEvaluation("偏低", "LOW", true, 12, "MEDIUM");
        }
        return new MetricEvaluation("正常", "NORMAL", false, 0, "LOW");
    }

    private MetricEvaluation evaluateHeartRate(BigDecimal value) {
        if (value.compareTo(new BigDecimal("120")) > 0) {
            return new MetricEvaluation("明显偏快", "HIGH", true, 20, "HIGH");
        }
        if (value.compareTo(new BigDecimal("100")) > 0) {
            return new MetricEvaluation("偏快", "ELEVATED", true, 10, "MEDIUM");
        }
        if (value.compareTo(new BigDecimal("50")) < 0) {
            return new MetricEvaluation("偏慢", "LOW", true, 10, "MEDIUM");
        }
        return new MetricEvaluation("正常", "NORMAL", false, 0, "LOW");
    }

    private MetricEvaluation evaluateBodyTemperature(BigDecimal value) {
        if (value.compareTo(new BigDecimal("38.5")) >= 0) {
            return new MetricEvaluation("高热", "HIGH", true, 20, "HIGH");
        }
        if (value.compareTo(new BigDecimal("37.3")) > 0) {
            return new MetricEvaluation("偏高", "ELEVATED", true, 10, "MEDIUM");
        }
        if (value.compareTo(new BigDecimal("35.5")) < 0) {
            return new MetricEvaluation("偏低", "LOW", true, 10, "MEDIUM");
        }
        return new MetricEvaluation("正常", "NORMAL", false, 0, "LOW");
    }

    private MetricEvaluation evaluateWeight(BigDecimal value) {
        BigDecimal rounded = value.setScale(1, RoundingMode.HALF_UP);
        if (rounded.compareTo(new BigDecimal("95")) >= 0) {
            return new MetricEvaluation("明显超重趋势", "HIGH", true, 8, "LOW");
        }
        if (rounded.compareTo(new BigDecimal("45")) < 0) {
            return new MetricEvaluation("偏轻趋势", "LOW", true, 8, "LOW");
        }
        return new MetricEvaluation("稳定", "NORMAL", false, 0, "LOW");
    }

    private record MetricEvaluation(String description,
                                    String level,
                                    boolean abnormal,
                                    int scorePenalty,
                                    String alertLevel) {
    }
}
