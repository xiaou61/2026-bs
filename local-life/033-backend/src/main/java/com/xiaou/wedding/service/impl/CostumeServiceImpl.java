package com.xiaou.wedding.service.impl;

import com.xiaou.wedding.entity.Costume;
import com.xiaou.wedding.entity.CostumeBorrow;
import com.xiaou.wedding.exception.BusinessException;
import com.xiaou.wedding.mapper.CostumeMapper;
import com.xiaou.wedding.service.CostumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CostumeServiceImpl implements CostumeService {

    @Autowired
    private CostumeMapper costumeMapper;

    @Override
    public List<Costume> list(String category, String status) {
        return costumeMapper.list(category, status);
    }

    @Override
    public Costume detail(Long id) {
        Costume costume = costumeMapper.findById(id);
        if (costume == null) {
            throw new BusinessException("Costume not found");
        }
        return costume;
    }

    @Override
    public void create(Costume costume) {
        costumeMapper.insert(costume);
    }

    @Override
    public void update(Costume costume) {
        if (costumeMapper.findById(costume.getId()) == null) {
            throw new BusinessException("Costume not found");
        }
        costumeMapper.update(costume);
    }

    @Override
    public void delete(Long id) {
        if (costumeMapper.findById(id) == null) {
            throw new BusinessException("Costume not found");
        }
        costumeMapper.logicDelete(id);
    }

    @Override
    public void borrow(CostumeBorrow borrow) {
        Costume costume = costumeMapper.findById(borrow.getCostumeId());
        if (costume == null) {
            throw new BusinessException("Costume not found");
        }
        borrow.setStatus(borrow.getStatus() == null ? "BORROWED" : borrow.getStatus());
        borrow.setBorrowDate(borrow.getBorrowDate() == null ? LocalDateTime.now() : borrow.getBorrowDate());
        costumeMapper.insertBorrow(borrow);
    }

    @Override
    public void returnCostume(Long borrowId) {
        costumeMapper.updateBorrowReturn(borrowId, LocalDateTime.now(), "RETURNED");
    }

    @Override
    public List<CostumeBorrow> borrowList(Long costumeId, String status) {
        return costumeMapper.borrowList(costumeId, status);
    }
}
