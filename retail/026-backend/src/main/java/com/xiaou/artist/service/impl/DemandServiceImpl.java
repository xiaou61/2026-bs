package com.xiaou.artist.service.impl;

import com.xiaou.artist.entity.Demand;
import com.xiaou.artist.mapper.DemandMapper;
import com.xiaou.artist.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DemandServiceImpl implements DemandService {
    
    @Autowired
    private DemandMapper demandMapper;
    
    @Override
    public Demand createDemand(Demand demand) {
        if (demand.getStatus() == null) {
            demand.setStatus("OPEN");
        }
        demandMapper.insert(demand);
        return demand;
    }
    
    @Override
    public List<Demand> getDemandsByUserId(Long userId) {
        return demandMapper.selectByUserId(userId);
    }
    
    @Override
    public List<Demand> getAllDemands() {
        return demandMapper.selectAll();
    }
    
    @Override
    public List<Demand> getOpenDemands() {
        return demandMapper.selectOpenDemands();
    }
    
    @Override
    public Demand getDemandById(Long id) {
        return demandMapper.selectById(id);
    }
    
    @Override
    public boolean updateDemand(Demand demand) {
        return demandMapper.update(demand) > 0;
    }
    
    @Override
    public boolean closeDemand(Long id) {
        return demandMapper.updateStatus(id, "CLOSED") > 0;
    }
    
    @Override
    public boolean deleteDemand(Long id) {
        return demandMapper.deleteById(id) > 0;
    }
}
