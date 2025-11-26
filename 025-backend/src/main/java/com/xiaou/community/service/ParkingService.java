package com.xiaou.community.service;

import com.xiaou.community.entity.Parking;
import java.util.List;

public interface ParkingService {
    void add(Parking parking);
    void update(Parking parking);
    void delete(Integer id);
    Parking getById(Integer id);
    List<Parking> getAll();
}
