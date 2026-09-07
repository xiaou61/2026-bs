package com.xiaou.community.mapper;

import com.xiaou.community.entity.Parking;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ParkingMapper {
    void insert(Parking parking);
    void update(Parking parking);
    void deleteById(Integer id);
    Parking findById(Integer id);
    List<Parking> findAll();
}
