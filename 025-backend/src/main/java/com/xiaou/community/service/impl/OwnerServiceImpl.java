package com.xiaou.community.service.impl;

import com.xiaou.community.entity.Owner;
import com.xiaou.community.mapper.FeeMapper;
import com.xiaou.community.mapper.OwnerMapper;
import com.xiaou.community.mapper.ParkingMapper;
import com.xiaou.community.mapper.RepairMapper;
import com.xiaou.community.mapper.VisitorMapper;
import com.xiaou.community.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    private OwnerMapper ownerMapper;
    @Autowired
    private FeeMapper feeMapper;
    @Autowired
    private RepairMapper repairMapper;
    @Autowired
    private VisitorMapper visitorMapper;
    @Autowired
    private ParkingMapper parkingMapper;

    @Override
    public void add(Owner owner) {
        ownerMapper.insert(owner);
    }

    @Override
    public void update(Owner owner) {
        ownerMapper.update(owner);
    }

    @Override
    public void delete(Integer id) {
        boolean hasRelatedFee = !feeMapper.findByOwnerId(id).isEmpty();
        boolean hasRelatedRepair = !repairMapper.findByOwnerId(id).isEmpty();
        boolean hasRelatedVisitor = !visitorMapper.findByOwnerId(id).isEmpty();
        boolean hasRelatedParking = parkingMapper.findAll().stream().anyMatch(item -> id.equals(item.getOwnerId()));
        if (hasRelatedFee || hasRelatedRepair || hasRelatedVisitor || hasRelatedParking) {
            throw new RuntimeException("该业主存在关联缴费、报修、访客或车位记录，暂不能删除");
        }
        ownerMapper.deleteById(id);
    }

    @Override
    public Owner getById(Integer id) {
        return ownerMapper.findById(id);
    }

    @Override
    public Owner getByUserId(Integer userId) {
        return ownerMapper.findByUserId(userId);
    }

    @Override
    public List<Owner> getAll() {
        return ownerMapper.findAll();
    }
}
