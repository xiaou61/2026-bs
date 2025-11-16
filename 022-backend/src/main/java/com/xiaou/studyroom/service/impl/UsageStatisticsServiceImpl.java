package com.xiaou.studyroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.studyroom.entity.Reservation;
import com.xiaou.studyroom.entity.Seat;
import com.xiaou.studyroom.entity.StudyRoom;
import com.xiaou.studyroom.entity.UsageStatistics;
import com.xiaou.studyroom.mapper.ReservationMapper;
import com.xiaou.studyroom.mapper.SeatMapper;
import com.xiaou.studyroom.mapper.StudyRoomMapper;
import com.xiaou.studyroom.mapper.UsageStatisticsMapper;
import com.xiaou.studyroom.service.UsageStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsageStatisticsServiceImpl extends ServiceImpl<UsageStatisticsMapper, UsageStatistics> implements UsageStatisticsService {

    @Autowired
    private StudyRoomMapper studyRoomMapper;

    @Autowired
    private SeatMapper seatMapper;

    @Autowired
    private ReservationMapper reservationMapper;

    @Override
    public void generateDailyStatistics() {
        LocalDate today = LocalDate.now();

        // 获取所有自习室
        QueryWrapper<StudyRoom> roomQuery = new QueryWrapper<>();
        roomQuery.eq("status", 1);
        List<StudyRoom> rooms = studyRoomMapper.selectList(roomQuery);

        for (StudyRoom room : rooms) {
            generateRoomStatistics(room.getId(), today);
        }
    }

    @Override
    public void generateDailyReport() {
        generateDailyStatistics();
    }

    private void generateRoomStatistics(Long roomId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        // 查询当天的所有预约
        QueryWrapper<Reservation> reservationQuery = new QueryWrapper<>();
        reservationQuery.eq("seat_id", roomId)
                       .between("start_time", startOfDay, endOfDay)
                       .in("status", 1, 2, 3); // 已预约、已签到、已完成

        List<Reservation> reservations = reservationMapper.selectList(reservationQuery);

        // 统计总预约数和实际签到数
        int totalReservations = reservations.size();
        int actualCheckins = (int) reservations.stream()
                .filter(r -> r.getStatus() == 2 || r.getStatus() == 3)
                .count();

        // 计算使用率
        double usageRate = calculateUsageRate(roomId, date);

        // 查找高峰时段
        LocalTime peakHour = findPeakHour(reservations);

        // 检查是否已存在统计记录
        QueryWrapper<UsageStatistics> existQuery = new QueryWrapper<>();
        existQuery.eq("room_id", roomId)
                 .eq("stat_date", date);

        UsageStatistics statistics = getOne(existQuery);
        if (statistics == null) {
            statistics = new UsageStatistics();
            statistics.setRoomId(roomId);
            statistics.setStatDate(date);
        }

        statistics.setTotalReservations(totalReservations);
        statistics.setActualCheckins(actualCheckins);
        statistics.setUsageRate(BigDecimal.valueOf(usageRate).setScale(2, RoundingMode.HALF_UP));
        statistics.setPeakHour(peakHour);

        saveOrUpdate(statistics);
    }

    @Override
    public List<UsageStatistics> getStatisticsByDateRange(LocalDate startDate, LocalDate endDate) {
        QueryWrapper<UsageStatistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("stat_date", startDate, endDate)
                   .orderByAsc("stat_date")
                   .orderByAsc("room_id");
        return list(queryWrapper);
    }

    @Override
    public Map<String, Object> getRoomUsageStatistics(Long roomId, LocalDate startDate, LocalDate endDate) {
        List<UsageStatistics> statistics = getStatisticsByDateRange(startDate, endDate);

        List<UsageStatistics> roomStatistics = statistics.stream()
                .filter(s -> s.getRoomId().equals(roomId))
                .collect(Collectors.toList());

        int totalReservations = roomStatistics.stream()
                .mapToInt(UsageStatistics::getTotalReservations)
                .sum();

        int totalCheckins = roomStatistics.stream()
                .mapToInt(UsageStatistics::getActualCheckins)
                .sum();

        double avgUsageRate = roomStatistics.stream()
                .mapToDouble(s -> s.getUsageRate().doubleValue())
                .average()
                .orElse(0.0);

        Map<String, Object> result = new HashMap<>();
        result.put("roomId", roomId);
        result.put("totalReservations", totalReservations);
        result.put("totalCheckins", totalCheckins);
        result.put("avgUsageRate", avgUsageRate);
        result.put("dailyStatistics", roomStatistics);

        return result;
    }

    @Override
    public Map<String, Object> getOverallStatistics(LocalDate startDate, LocalDate endDate) {
        List<UsageStatistics> statistics = getStatisticsByDateRange(startDate, endDate);

        int totalReservations = statistics.stream()
                .mapToInt(UsageStatistics::getTotalReservations)
                .sum();

        int totalCheckins = statistics.stream()
                .mapToInt(UsageStatistics::getActualCheckins)
                .sum();

        double avgUsageRate = statistics.stream()
                .mapToDouble(s -> s.getUsageRate().doubleValue())
                .average()
                .orElse(0.0);

        // 按房间分组统计
        Map<Long, List<UsageStatistics>> roomGrouped = statistics.stream()
                .collect(Collectors.groupingBy(UsageStatistics::getRoomId));

        Map<Long, String> roomNames = new HashMap<>();
        QueryWrapper<StudyRoom> roomQuery = new QueryWrapper<>();
        List<StudyRoom> rooms = studyRoomMapper.selectList(roomQuery);
        for (StudyRoom room : rooms) {
            roomNames.put(room.getId(), room.getRoomName());
        }

        List<Map<String, Object>> roomStatistics = new ArrayList<>();
        for (Map.Entry<Long, List<UsageStatistics>> entry : roomGrouped.entrySet()) {
            Long roomId = entry.getKey();
            List<UsageStatistics> roomStats = entry.getValue();

            int roomTotalRes = roomStats.stream()
                    .mapToInt(UsageStatistics::getTotalReservations)
                    .sum();

            double roomUsageRate = roomStats.stream()
                    .mapToDouble(s -> s.getUsageRate().doubleValue())
                    .average()
                    .orElse(0.0);

            Map<String, Object> roomStat = new HashMap<>();
            roomStat.put("roomId", roomId);
            roomStat.put("roomName", roomNames.get(roomId));
            roomStat.put("totalReservations", roomTotalRes);
            roomStat.put("avgUsageRate", roomUsageRate);
            roomStatistics.add(roomStat);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalReservations", totalReservations);
        result.put("totalCheckins", totalCheckins);
        result.put("avgUsageRate", avgUsageRate);
        result.put("roomStatistics", roomStatistics);
        result.put("dailyStatistics", statistics);

        return result;
    }

    @Override
    public List<Map<String, Object>> getPeakHoursStatistics(Long roomId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        QueryWrapper<Reservation> reservationQuery = new QueryWrapper<>();
        reservationQuery.eq("seat_id", roomId)
                       .between("start_time", startOfDay, endOfDay)
                       .eq("status", 2); // 已签到的预约

        List<Reservation> reservations = reservationMapper.selectList(reservationQuery);

        // 按小时统计
        Map<Integer, Long> hourlyCount = new HashMap<>();
        for (int hour = 8; hour <= 22; hour++) {
            final int currentHour = hour;
            long count = reservations.stream()
                    .filter(r -> {
                        int startHour = r.getStartTime().getHour();
                        int endHour = r.getEndTime().getHour();
                        return startHour <= currentHour && currentHour < endHour;
                    })
                    .count();
            hourlyCount.put(hour, count);
        }

        // 转换为列表格式
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<Integer, Long> entry : hourlyCount.entrySet()) {
            Map<String, Object> hourStat = new HashMap<>();
            hourStat.put("hour", entry.getKey());
            hourStat.put("count", entry.getValue());
            result.add(hourStat);
        }

        return result;
    }

    @Override
    public double calculateUsageRate(Long roomId, LocalDate date) {
        // 获取自习室的总座位数
        QueryWrapper<Seat> seatQuery = new QueryWrapper<>();
        seatQuery.eq("room_id", roomId);
        int totalSeats = Math.toIntExact(seatMapper.selectCount(seatQuery));

        if (totalSeats == 0) {
            return 0.0;
        }

        // 查询当天已使用的座位数
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        QueryWrapper<Reservation> reservationQuery = new QueryWrapper<>();
        reservationQuery.eq("seat_id", roomId)
                       .between("start_time", startOfDay, endOfDay)
                       .eq("status", 2); // 已签到状态

        long usedSeats = reservationMapper.selectCount(reservationQuery);

        return (double) usedSeats / totalSeats * 100;
    }

    private LocalTime findPeakHour(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            return null;
        }

        // 按小时统计签到时间
        Map<Integer, Long> hourlyCount = new HashMap<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCheckInTime() != null) {
                int hour = reservation.getCheckInTime().getHour();
                hourlyCount.put(hour, hourlyCount.getOrDefault(hour, 0L) + 1);
            }
        }

        if (hourlyCount.isEmpty()) {
            return null;
        }

        // 找到最多的时段
        int peakHour = hourlyCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(12);

        return LocalTime.of(peakHour, 0);
    }
}