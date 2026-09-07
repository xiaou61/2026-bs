package com.xiaou.seniorhealth.schedule;

import com.xiaou.seniorhealth.domain.Appointment;
import com.xiaou.seniorhealth.domain.FollowUp;
import com.xiaou.seniorhealth.domain.Notification;
import com.xiaou.seniorhealth.repository.AppointmentRepository;
import com.xiaou.seniorhealth.repository.FollowUpRepository;
import com.xiaou.seniorhealth.repository.NotificationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReminderScheduler {
    private final AppointmentRepository appointmentRepository;
    private final FollowUpRepository followUpRepository;
    private final NotificationRepository notificationRepository;
    public ReminderScheduler(AppointmentRepository appointmentRepository, FollowUpRepository followUpRepository, NotificationRepository notificationRepository) {
        this.appointmentRepository = appointmentRepository;
        this.followUpRepository = followUpRepository;
        this.notificationRepository = notificationRepository;
    }
    @Scheduled(fixedDelay = 300000)
    public void remindAppointments() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime soon = now.plusHours(12);
        List<Appointment> list = appointmentRepository.findUpcomingAll(now, soon);
        for (Appointment a : list) {
            Notification n = new Notification();
            n.setUserId(a.getDoctorUserId());
            n.setTitle("预约提醒");
            n.setContent("即将开始的预约:" + a.getId());
            n.setStatus("UNREAD");
            n.setCreatedAt(LocalDateTime.now());
            notificationRepository.save(n);
        }
    }
    @Scheduled(cron = "0 0 * * * *")
    public void remindFollowUps() {
        LocalDate from = LocalDate.now();
        LocalDate to = from.plusDays(1);
        List<FollowUp> list = followUpRepository.findDueAll(from, to);
        for (FollowUp f : list) {
            Notification n = new Notification();
            n.setUserId(f.getDoctorUserId());
            n.setTitle("随访提醒");
            n.setContent("即将到期的随访:" + f.getId());
            n.setStatus("UNREAD");
            n.setCreatedAt(LocalDateTime.now());
            notificationRepository.save(n);
        }
    }
}
