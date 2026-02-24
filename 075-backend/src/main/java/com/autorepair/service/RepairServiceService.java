package com.autorepair.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.autorepair.common.BusinessException;
import com.autorepair.entity.RepairService;
import com.autorepair.mapper.RepairServiceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class RepairServiceService {

    @Resource
    private RepairServiceMapper serviceMapper;

    public Page<RepairService> page(Integer pageNum, Integer pageSize, String title, Long categoryId, String serviceName, Integer status, Long sellerId) {
        QueryWrapper<RepairService> wrapper = new QueryWrapper<>();
        if (title != null && !title.trim().isEmpty()) {
            wrapper.like("title", title.trim());
        }
        if (categoryId != null) {
            wrapper.eq("category_id", categoryId);
        }
        if (serviceName != null && !serviceName.trim().isEmpty()) {
            wrapper.like("service_name", serviceName.trim());
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (sellerId != null) {
            wrapper.eq("seller_id", sellerId);
        }
        wrapper.orderByDesc("id");
        return serviceMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public Page<RepairService> list(Integer pageNum, Integer pageSize, String title, Long categoryId, String serviceName) {
        QueryWrapper<RepairService> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        if (title != null && !title.trim().isEmpty()) {
            wrapper.like("title", title.trim());
        }
        if (categoryId != null) {
            wrapper.eq("category_id", categoryId);
        }
        if (serviceName != null && !serviceName.trim().isEmpty()) {
            wrapper.like("service_name", serviceName.trim());
        }
        wrapper.orderByDesc("id");
        return serviceMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public RepairService getById(Long id) {
        RepairService item = serviceMapper.selectById(id);
        if (item == null) {
            throw new BusinessException("维修项目不存在");
        }
        item.setViewCount((item.getViewCount() == null ? 0 : item.getViewCount()) + 1);
        serviceMapper.updateById(item);
        return item;
    }

    public RepairService mustGetById(Long id) {
        RepairService item = serviceMapper.selectById(id);
        if (item == null) {
            throw new BusinessException("维修项目不存在");
        }
        return item;
    }

    public void save(RepairService service, Long userId, String role) {
        checkItemParams(service);
        if (service.getId() == null) {
            if (service.getSellerId() == null) {
                service.setSellerId(userId);
            }
            if (!"ADMIN".equals(role)) {
                service.setSellerId(userId);
            }
            if (service.getStatus() == null) {
                service.setStatus(1);
            }
            if (service.getViewCount() == null) {
                service.setViewCount(0);
            }
            if (service.getBookedCount() == null) {
                service.setBookedCount(0);
            }
            serviceMapper.insert(service);
        } else {
            RepairService db = serviceMapper.selectById(service.getId());
            if (db == null) {
                throw new BusinessException("维修项目不存在");
            }
            if (!"ADMIN".equals(role) && !db.getSellerId().equals(userId)) {
                throw new BusinessException("无权限修改该维修项目");
            }
            if (service.getViewCount() == null) {
                service.setViewCount(db.getViewCount());
            }
            if (service.getBookedCount() == null) {
                service.setBookedCount(db.getBookedCount());
            }
            if (service.getStatus() == null) {
                service.setStatus(db.getStatus());
            }
            service.setSellerId(db.getSellerId());
            serviceMapper.updateById(service);
        }
    }

    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("维修项目状态不合法");
        }
        RepairService item = mustGetById(id);
        item.setStatus(status);
        serviceMapper.updateById(item);
    }

    public void deleteById(Long id, Long userId, String role) {
        RepairService item = mustGetById(id);
        if (!"ADMIN".equals(role) && !item.getSellerId().equals(userId)) {
            throw new BusinessException("无权限删除该维修项目");
        }
        serviceMapper.deleteById(id);
    }

    public void decreaseStockAndIncreaseBooked(Long serviceId, Integer quantity) {
        RepairService item = mustGetById(serviceId);
        int currentStock = item.getStock() == null ? 0 : item.getStock();
        if (currentStock < quantity) {
            throw new BusinessException("可预约名额不足");
        }
        item.setStock(currentStock - quantity);
        item.setBookedCount((item.getBookedCount() == null ? 0 : item.getBookedCount()) + quantity);
        serviceMapper.updateById(item);
    }

    public void restoreStockAndBooked(Long serviceId, Integer quantity) {
        RepairService item = mustGetById(serviceId);
        item.setStock((item.getStock() == null ? 0 : item.getStock()) + quantity);
        int sold = item.getBookedCount() == null ? 0 : item.getBookedCount();
        item.setBookedCount(Math.max(0, sold - quantity));
        serviceMapper.updateById(item);
    }

    public List<RepairService> hotServices(int limit) {
        return serviceMapper.selectList(new QueryWrapper<RepairService>()
                .eq("status", 1)
                .orderByDesc("booked_count")
                .last("limit " + limit));
    }

    private void checkItemParams(RepairService item) {
        if (item == null) {
            throw new BusinessException("维修项目参数不能为空");
        }
        if (item.getCategoryId() == null) {
            throw new BusinessException("请选择维修分类");
        }
        if (item.getTitle() == null || item.getTitle().trim().isEmpty()) {
            throw new BusinessException("项目标题不能为空");
        }
        item.setTitle(item.getTitle().trim());
        if (item.getTitle().length() > 120) {
            throw new BusinessException("项目标题不能超过120字符");
        }
        if (item.getServiceName() == null || item.getServiceName().trim().isEmpty()) {
            throw new BusinessException("服务名称不能为空");
        }
        item.setServiceName(item.getServiceName().trim());
        if (item.getServiceName().length() > 80) {
            throw new BusinessException("服务名称不能超过80字符");
        }
        if (item.getStoreName() != null) {
            item.setStoreName(item.getStoreName().trim());
            if (item.getStoreName().length() > 80) {
                throw new BusinessException("门店名称不能超过80字符");
            }
        }
        if (item.getVehicleType() != null) {
            item.setVehicleType(item.getVehicleType().trim());
            if (item.getVehicleType().length() > 40) {
                throw new BusinessException("适用车型不能超过40字符");
            }
        }
        if (item.getBookingType() != null) {
            item.setBookingType(item.getBookingType().trim());
            if (item.getBookingType().length() > 30) {
                throw new BusinessException("预约方式不能超过30字符");
            }
        }
        if (item.getCover() != null) {
            item.setCover(item.getCover().trim());
            if (item.getCover().length() > 255) {
                throw new BusinessException("封面地址不能超过255字符");
            }
        }
        if (item.getDescription() != null) {
            item.setDescription(item.getDescription().trim());
        }
        if (item.getPrice() == null || item.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("项目价格必须大于0");
        }
        if (item.getStock() == null || item.getStock() < 0) {
            throw new BusinessException("可预约名额不能小于0");
        }
        if (item.getStatus() != null && item.getStatus() != 0 && item.getStatus() != 1) {
            throw new BusinessException("维修项目状态不合法");
        }
    }
}




