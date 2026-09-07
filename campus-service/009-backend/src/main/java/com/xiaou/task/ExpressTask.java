package com.xiaou.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaou.entity.Express;
import com.xiaou.entity.Notification;
import com.xiaou.service.ExpressService;
import com.xiaou.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class ExpressTask {

    @Autowired
    private ExpressService expressService;

    @Autowired
    private NotificationService notificationService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void updateOverdueStatus() {
        System.out.println("开始执行超期计算任务: " + LocalDateTime.now());

        QueryWrapper<Express> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);
        List<Express> expressList = expressService.list(wrapper);

        int updateCount = 0;
        int notifyCount = 0;

        for (Express express : expressList) {
            long days = ChronoUnit.DAYS.between(express.getInTime().toLocalDate(), LocalDateTime.now().toLocalDate());
            int overdueDays = (int) Math.max(0, days - 3);

            if (overdueDays > 0) {
                BigDecimal overdueFee = calculateOverdueFee(overdueDays);
                express.setOverdueDays(overdueDays);
                express.setOverdueFee(overdueFee);

                if (overdueDays >= 7) {
                    express.setStatus(2);
                }

                expressService.updateById(express);
                updateCount++;

                if ((overdueDays == 1 || overdueDays == 3) && express.getRecipientId() != null) {
                    QueryWrapper<Notification> notificationWrapper = new QueryWrapper<>();
                    notificationWrapper.eq("user_id", express.getRecipientId());
                    notificationWrapper.eq("express_id", express.getId());
                    notificationWrapper.eq("type", "OVERDUE");
                    notificationWrapper.apply("DATE(create_time) = CURDATE()");
                    
                    if (notificationService.count(notificationWrapper) == 0) {
                        Notification notification = new Notification();
                        notification.setUserId(express.getRecipientId());
                        notification.setType("OVERDUE");
                        notification.setTitle("超期提醒");
                        notification.setContent(String.format(
                                "您的快递(%s)已超期%d天，超期费用：¥%.2f，请尽快取件。取件码：%s",
                                express.getTrackingNumber(),
                                overdueDays,
                                overdueFee.doubleValue(),
                                express.getPickupCode()
                        ));
                        notification.setExpressId(express.getId());
                        notification.setIsRead(0);
                        notification.setSendMethod("SYSTEM");
                        notification.setSendStatus(1);
                        notificationService.save(notification);
                        notifyCount++;
                    }
                }

                if (overdueDays == 15 && express.getRecipientId() != null) {
                    QueryWrapper<Notification> notificationWrapper = new QueryWrapper<>();
                    notificationWrapper.eq("user_id", express.getRecipientId());
                    notificationWrapper.eq("express_id", express.getId());
                    notificationWrapper.eq("type", "RETURN");
                    notificationWrapper.apply("DATE(create_time) = CURDATE()");
                    
                    if (notificationService.count(notificationWrapper) == 0) {
                        Notification notification = new Notification();
                        notification.setUserId(express.getRecipientId());
                        notification.setType("RETURN");
                        notification.setTitle("长期滞留通知");
                        notification.setContent(String.format(
                                "您的快递(%s)已超期%d天，将作为长期滞留件处理，如需取件请尽快联系代收点。取件码：%s",
                                express.getTrackingNumber(),
                                overdueDays,
                                express.getPickupCode()
                        ));
                        notification.setExpressId(express.getId());
                        notification.setIsRead(0);
                        notification.setSendMethod("SYSTEM");
                        notification.setSendStatus(1);
                        notificationService.save(notification);
                        notifyCount++;
                    }
                }
            }
        }

        System.out.println(String.format("超期计算任务完成: 更新%d条记录，发送%d条通知", updateCount, notifyCount));
    }

    private BigDecimal calculateOverdueFee(int overdueDays) {
        if (overdueDays <= 0) {
            return BigDecimal.ZERO;
        }
        
        if (overdueDays <= 4) {
            return BigDecimal.valueOf(overdueDays * 1.0);
        } else {
            return BigDecimal.valueOf(4 * 1.0 + (overdueDays - 4) * 2.0);
        }
    }
}

