package com.railway.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.railway.common.BusinessException;
import com.railway.common.PageResult;
import com.railway.entity.Carriage;
import com.railway.entity.Schedule;
import com.railway.entity.Seat;
import com.railway.entity.Station;
import com.railway.entity.Train;
import com.railway.mapper.ScheduleMapper;
import com.railway.mapper.SeatMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScheduleService {

    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private SeatMapper seatMapper;

    @Resource
    private TrainService trainService;

    @Resource
    private StationService stationService;

    @Resource
    private CarriageService carriageService;

    public PageResult<Schedule> page(Integer pageNum, Integer pageSize, Long trainId, Long departureStationId, Long arrivalStationId, String travelDate, String saleStatus, Integer status) {
        Page<Schedule> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Schedule> wrapper = new LambdaQueryWrapper<Schedule>()
                .eq(trainId != null, Schedule::getTrainId, trainId)
                .eq(departureStationId != null, Schedule::getDepartureStationId, departureStationId)
                .eq(arrivalStationId != null, Schedule::getArrivalStationId, arrivalStationId)
                .eq(StringUtils.hasText(travelDate), Schedule::getTravelDate, travelDate)
                .eq(StringUtils.hasText(saleStatus), Schedule::getSaleStatus, trim(saleStatus))
                .eq(status != null, Schedule::getStatus, status)
                .orderByAsc(Schedule::getDepartureTime);
        Page<Schedule> resultPage = scheduleMapper.selectPage(page, wrapper);
        PageResult<Schedule> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<Schedule> enabledList() {
        return scheduleMapper.selectList(new LambdaQueryWrapper<Schedule>()
                .eq(Schedule::getStatus, 1)
                .eq(Schedule::getSaleStatus, "ON_SALE")
                .orderByAsc(Schedule::getDepartureTime));
    }

    public Schedule getById(Long id) {
        Schedule schedule = scheduleMapper.selectById(id);
        if (schedule == null) {
            throw new BusinessException("班次不存在");
        }
        return schedule;
    }

    public void save(Schedule schedule) {
        validate(schedule);
        Train train = trainService.getById(schedule.getTrainId());
        Station departureStation = stationService.getById(schedule.getDepartureStationId());
        Station arrivalStation = stationService.getById(schedule.getArrivalStationId());
        Carriage carriage = carriageService.getById(schedule.getCarriageId());
        if (departureStation.getId().equals(arrivalStation.getId())) {
            throw new BusinessException("出发站和到达站不能相同");
        }
        int totalSeats = carriage.getCoachCount() * carriage.getSeatRows() * carriage.getSeatCols();
        if (schedule.getId() == null) {
            schedule.setTotalSeats(totalSeats);
            schedule.setAvailableSeats(totalSeats);
            schedule.setSaleStatus(StringUtils.hasText(schedule.getSaleStatus()) ? schedule.getSaleStatus().trim() : "ON_SALE");
            schedule.setStatus(schedule.getStatus() == null ? 1 : schedule.getStatus());
            scheduleMapper.insert(schedule);
            return;
        }
        Schedule db = getById(schedule.getId());
        db.setTrainId(train.getId());
        db.setCarriageId(carriage.getId());
        db.setDepartureStationId(departureStation.getId());
        db.setArrivalStationId(arrivalStation.getId());
        db.setTravelDate(schedule.getTravelDate());
        db.setDepartureTime(schedule.getDepartureTime());
        db.setArrivalTime(schedule.getArrivalTime());
        db.setBasePrice(schedule.getBasePrice());
        db.setSaleStatus(StringUtils.hasText(schedule.getSaleStatus()) ? schedule.getSaleStatus().trim() : db.getSaleStatus());
        db.setStatus(schedule.getStatus() == null ? db.getStatus() : schedule.getStatus());
        Long seatCount = seatMapper.selectCount(new LambdaQueryWrapper<Seat>().eq(Seat::getScheduleId, db.getId()));
        if (seatCount == null || seatCount == 0) {
            db.setTotalSeats(totalSeats);
            db.setAvailableSeats(totalSeats);
        }
        scheduleMapper.updateById(db);
    }

    public void deleteById(Long id) {
        seatMapper.delete(new LambdaQueryWrapper<Seat>().eq(Seat::getScheduleId, id));
        scheduleMapper.deleteById(id);
    }

    public void changeAvailableSeats(Long scheduleId, int delta) {
        Schedule schedule = getById(scheduleId);
        int totalSeats = schedule.getTotalSeats() == null ? 0 : schedule.getTotalSeats();
        int availableSeats = schedule.getAvailableSeats() == null ? 0 : schedule.getAvailableSeats();
        int newValue = availableSeats + delta;
        if (newValue < 0 || newValue > totalSeats) {
            throw new BusinessException("余票数量异常");
        }
        schedule.setAvailableSeats(newValue);
        scheduleMapper.updateById(schedule);
    }

    public Long countAll() {
        return scheduleMapper.selectCount(new LambdaQueryWrapper<Schedule>().eq(Schedule::getStatus, 1));
    }

    private void validate(Schedule schedule) {
        if (schedule == null || schedule.getTrainId() == null || schedule.getCarriageId() == null
                || schedule.getDepartureStationId() == null || schedule.getArrivalStationId() == null) {
            throw new BusinessException("班次基础信息不完整");
        }
        if (schedule.getTravelDate() == null || schedule.getDepartureTime() == null || schedule.getArrivalTime() == null) {
            throw new BusinessException("发车和到达时间不能为空");
        }
        if (!schedule.getArrivalTime().isAfter(schedule.getDepartureTime())) {
            throw new BusinessException("到达时间必须晚于发车时间");
        }
        if (schedule.getBasePrice() == null || schedule.getBasePrice().doubleValue() <= 0) {
            throw new BusinessException("票价必须大于0");
        }
    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }
}
