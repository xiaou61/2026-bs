package com.xiaou.health.service;

import com.xiaou.health.dto.ConsultationCreateRequest;
import com.xiaou.health.entity.Consultation;
import com.xiaou.health.repository.ConsultationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultationService {
    private final ConsultationRepository consultationRepository;

    public ConsultationService(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

    @Transactional
    public Consultation createConsultation(Long patientId, ConsultationCreateRequest request) {
        Consultation consultation = new Consultation();
        consultation.setPatientId(patientId);
        consultation.setDoctorId(request.getDoctorId());
        consultation.setTitle(request.getTitle());
        consultation.setQuestion(request.getQuestion());
        consultation.setStatus("PENDING");
        
        return consultationRepository.save(consultation);
    }

    public List<Consultation> getPatientConsultations(Long patientId) {
        return consultationRepository.findByPatientIdOrderByCreateTimeDesc(patientId);
    }

    public List<Consultation> getDoctorConsultations(Long doctorId) {
        return consultationRepository.findByDoctorIdOrderByCreateTimeDesc(doctorId);
    }

    public List<Consultation> getDoctorPendingConsultations(Long doctorId) {
        return consultationRepository.findByDoctorIdAndStatus(doctorId, "PENDING");
    }

    public Consultation getConsultationDetail(Long id) {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("咨询记录不存在"));
    }

    @Transactional
    public Consultation answerConsultation(Long id, String answer, Long doctorId) {
        Consultation consultation = getConsultationDetail(id);
        
        if (!consultation.getDoctorId().equals(doctorId)) {
            throw new RuntimeException("无权回答此咨询");
        }
        
        consultation.setAnswer(answer);
        consultation.setStatus("ANSWERED");
        consultation.setAnsweredTime(LocalDateTime.now());
        
        return consultationRepository.save(consultation);
    }

    @Transactional
    public Consultation rateConsultation(Long id, Integer rating, String feedback, Long patientId) {
        Consultation consultation = getConsultationDetail(id);
        
        if (!consultation.getPatientId().equals(patientId)) {
            throw new RuntimeException("无权评价此咨询");
        }
        
        if (!"ANSWERED".equals(consultation.getStatus())) {
            throw new RuntimeException("只能评价已回答的咨询");
        }
        
        consultation.setRating(rating);
        consultation.setFeedback(feedback);
        
        return consultationRepository.save(consultation);
    }
}
