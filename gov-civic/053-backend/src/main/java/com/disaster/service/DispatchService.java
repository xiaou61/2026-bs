package com.disaster.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.disaster.common.BusinessException;
import com.disaster.entity.Dispatch;
import com.disaster.entity.DispatchItem;
import com.disaster.mapper.DispatchItemMapper;
import com.disaster.mapper.DispatchMapper;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DispatchService {

    @Autowired
    private DispatchMapper dispatchMapper;

    @Autowired
    private DispatchItemMapper itemMapper;

    @Autowired
    private StockService stockService;

    @Transactional
    public void create(Dispatch dispatch, List<DispatchItem> items, Long applicantId) {
        dispatch.setDispatchNo("DS" + IdUtil.getSnowflakeNextIdStr());
        dispatch.setApplicantId(applicantId);
        dispatch.setStatus(0);
        dispatchMapper.insert(dispatch);
        for (DispatchItem item : items) {
            item.setDispatchId(dispatch.getId());
            itemMapper.insert(item);
        }
    }

    public Page<Dispatch> page(int pageNum, int pageSize, Integer status, Integer priority) {
        LambdaQueryWrapper<Dispatch> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(Dispatch::getStatus, status);
        }
        if (priority != null) {
            wrapper.eq(Dispatch::getPriority, priority);
        }
        wrapper.orderByDesc(Dispatch::getCreateTime);
        return dispatchMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public Dispatch getById(Long id) {
        return dispatchMapper.selectById(id);
    }

    public List<DispatchItem> getItems(Long dispatchId) {
        return itemMapper.selectList(new LambdaQueryWrapper<DispatchItem>()
                .eq(DispatchItem::getDispatchId, dispatchId));
    }

    @Transactional
    public void approve(Long id, Long approverId, String remark, boolean approved) {
        Dispatch dispatch = dispatchMapper.selectById(id);
        if (dispatch.getStatus() != 0) {
            throw new BusinessException("当前状态不允许审批");
        }
        dispatch.setApproverId(approverId);
        dispatch.setApproveTime(LocalDateTime.now());
        dispatch.setApproveRemark(remark);
        if (approved) {
            dispatch.setStatus(1);
            List<DispatchItem> items = getItems(id);
            for (DispatchItem item : items) {
                stockService.stockOut(dispatch.getFromWarehouseId(), item.getMaterialId(), 
                        item.getQuantity(), "调度出库:" + dispatch.getDispatchNo(), approverId, "调度审批通过");
            }
        } else {
            dispatch.setStatus(4);
        }
        dispatchMapper.updateById(dispatch);
    }

    public void updateStatus(Long id, Integer status) {
        Dispatch dispatch = new Dispatch();
        dispatch.setId(id);
        dispatch.setStatus(status);
        if (status == 3) {
            dispatch.setCompleteTime(LocalDateTime.now());
        }
        dispatchMapper.updateById(dispatch);
    }

    public void delete(Long id) {
        dispatchMapper.deleteById(id);
        itemMapper.delete(new LambdaQueryWrapper<DispatchItem>().eq(DispatchItem::getDispatchId, id));
    }
}
