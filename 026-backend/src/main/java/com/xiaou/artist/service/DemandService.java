package com.xiaou.artist.service;

import com.xiaou.artist.entity.Demand;
import java.util.List;

public interface DemandService {
    Demand createDemand(Demand demand);
    List<Demand> getDemandsByUserId(Long userId);
    List<Demand> getAllDemands();
    List<Demand> getOpenDemands();
    Demand getDemandById(Long id);
    boolean updateDemand(Demand demand);
    boolean closeDemand(Long id);
    boolean deleteDemand(Long id);
}
