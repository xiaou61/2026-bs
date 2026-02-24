package com.bike.service;

import com.bike.entity.FaultReport;
import com.bike.mapper.BikeMapper;
import com.bike.mapper.FaultReportMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class FaultService {

    @Autowired
    private FaultReportMapper faultReportMapper;

    @Autowired
    private BikeMapper bikeMapper;

    @Transactional
    public void report(FaultReport report) {
        faultReportMapper.insert(report);
        bikeMapper.updateStatus(report.getBikeId(), 3);
    }

    public PageInfo<FaultReport> getList(Integer pageNum, Integer pageSize, Integer status, Integer type) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(faultReportMapper.findList(status, type));
    }

    public PageInfo<FaultReport> getMyList(Long userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(faultReportMapper.findByUserId(userId));
    }

    @Transactional
    public void handle(Long id, Long handlerId, String handleResult, Integer status) {
        FaultReport report = faultReportMapper.findById(id);
        report.setHandlerId(handlerId);
        report.setHandleResult(handleResult);
        report.setHandleTime(new Date());
        report.setStatus(status);
        faultReportMapper.update(report);
        if (status == 2) {
            bikeMapper.updateStatus(report.getBikeId(), 1);
        }
    }
}
