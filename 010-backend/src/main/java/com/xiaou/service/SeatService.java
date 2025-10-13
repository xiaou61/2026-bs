package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.entity.Area;
import com.xiaou.entity.Floor;
import com.xiaou.entity.Seat;
import com.xiaou.mapper.AreaMapper;
import com.xiaou.mapper.FloorMapper;
import com.xiaou.mapper.SeatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeatService {

    @Autowired
    private FloorMapper floorMapper;
    
    @Autowired
    private AreaMapper areaMapper;
    
    @Autowired
    private SeatMapper seatMapper;

    public List<Floor> listFloors() {
        LambdaQueryWrapper<Floor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Floor::getStatus, 1);
        wrapper.orderByAsc(Floor::getSortOrder);
        return floorMapper.selectList(wrapper);
    }

    public List<Area> listAreas(Long floorId) {
        LambdaQueryWrapper<Area> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Area::getStatus, 1);
        if (floorId != null) {
            wrapper.eq(Area::getFloorId, floorId);
        }
        wrapper.orderByAsc(Area::getSortOrder);
        return areaMapper.selectList(wrapper);
    }

    public List<Seat> listSeats(Long areaId) {
        LambdaQueryWrapper<Seat> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Seat::getStatus, 1);
        if (areaId != null) {
            wrapper.eq(Seat::getAreaId, areaId);
        }
        return seatMapper.selectList(wrapper);
    }

    public Seat getById(Long id) {
        return seatMapper.selectById(id);
    }

    public void updateHotScore(Long seatId) {
        Seat seat = seatMapper.selectById(seatId);
        seat.setTotalBookCount(seat.getTotalBookCount() + 1);
        seat.setHotScore(seat.getHotScore() + 1);
        seat.setUpdateTime(LocalDateTime.now());
        seatMapper.updateById(seat);
    }

    public Floor saveFloor(Floor floor) {
        if (floor.getId() == null) {
            floor.setCreateTime(LocalDateTime.now());
        }
        floor.setUpdateTime(LocalDateTime.now());
        if (floor.getId() == null) {
            floorMapper.insert(floor);
        } else {
            floorMapper.updateById(floor);
        }
        return floor;
    }

    public Area saveArea(Area area) {
        if (area.getId() == null) {
            area.setCreateTime(LocalDateTime.now());
        }
        area.setUpdateTime(LocalDateTime.now());
        if (area.getId() == null) {
            areaMapper.insert(area);
        } else {
            areaMapper.updateById(area);
        }
        return area;
    }

    public Seat saveSeat(Seat seat) {
        if (seat.getId() == null) {
            seat.setCreateTime(LocalDateTime.now());
            seat.setHotScore(0);
            seat.setTotalBookCount(0);
        }
        seat.setUpdateTime(LocalDateTime.now());
        if (seat.getId() == null) {
            seatMapper.insert(seat);
        } else {
            seatMapper.updateById(seat);
        }
        return seat;
    }

    public void deleteFloor(Long id) {
        floorMapper.deleteById(id);
    }

    public void deleteArea(Long id) {
        areaMapper.deleteById(id);
    }

    public void deleteSeat(Long id) {
        seatMapper.deleteById(id);
    }
}

