package com.greenhouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.greenhouse.entity.MaintenanceTicket;
import com.greenhouse.mapper.MaintenanceTicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaintenanceTicketService {
    private final MaintenanceTicketMapper maintenanceTicketMapper;

    public PageInfo<MaintenanceTicket> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(maintenanceTicketMapper.selectPage(keyword, status));
    }

    public void save(MaintenanceTicket entity) {
        if (entity.getId() == null) maintenanceTicketMapper.insert(entity);
        else maintenanceTicketMapper.update(entity);
    }

    public void delete(Long id) {
        maintenanceTicketMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        maintenanceTicketMapper.updateStatus(id, status);
    }
}
