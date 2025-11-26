package com.xiaou.community.service.impl;

import com.xiaou.community.entity.Parking;
import com.xiaou.community.mapper.ParkingMapper;
import com.xiaou.community.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService {
    @Autowired
    private ParkingMapper parkingMapper;

    @Override
    public void add(Parking parking) {
        parking.setStatus("FREE");
        parkingMapper.insert(parking);
    }

    @Override
    public void update(Parking parking) {
        parkingMapper.update(parking);
    }

    @Override
    public void delete(Integer id) {
        parkingMapper.deleteById(id);
    }

    @Override
    public Parking getById(Integer id) {
        return parkingMapper.findById(id);
    }

    @Override
    public List<Parking> getAll() {
        return parkingMapper.findAll();
    }
}
