package com.community.parking.repository;

import com.community.parking.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>, QuerydslPredicateExecutor<Vehicle> {
    List<Vehicle> findByUserId(Long userId);
    
    Optional<Vehicle> findByPlateNumber(String plateNumber);
    
    boolean existsByPlateNumber(String plateNumber);
}
