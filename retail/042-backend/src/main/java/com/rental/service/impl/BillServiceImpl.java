package com.rental.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rental.common.BusinessException;
import com.rental.entity.Bill;
import com.rental.entity.Contract;
import com.rental.entity.House;
import com.rental.entity.User;
import com.rental.mapper.BillMapper;
import com.rental.mapper.ContractMapper;
import com.rental.mapper.HouseMapper;
import com.rental.mapper.UserMapper;
import com.rental.service.BillService;
import com.rental.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageService messageService;

    @Override
    public void generateFirstBill(Contract contract) {
        Bill bill = new Bill();
        bill.setBillNo("ZD" + IdUtil.getSnowflakeNextIdStr());
        bill.setContractId(contract.getId());
        bill.setTenantId(contract.getTenantId());
        bill.setLandlordId(contract.getLandlordId());
        bill.setAmount(contract.getMonthlyRent());
        bill.setBillMonth(contract.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM")));
        bill.setDueDate(contract.getStartDate().plusDays(contract.getPaymentDay() - 1));
        bill.setPaidAmount(BigDecimal.ZERO);
        bill.setStatus(0);

        billMapper.insert(bill);

        messageService.send(contract.getTenantId(), "新账单待支付",
                "您有一笔租金账单待支付，请及时处理", "bill");
    }

    @Override
    public IPage<Map<String, Object>> getList(Long userId, String role, int page, int size, Integer status) {
        LambdaQueryWrapper<Bill> wrapper = new LambdaQueryWrapper<>();

        if ("landlord".equals(role)) {
            wrapper.eq(Bill::getLandlordId, userId);
        } else {
            wrapper.eq(Bill::getTenantId, userId);
        }

        if (status != null) {
            wrapper.eq(Bill::getStatus, status);
        }

        wrapper.orderByDesc(Bill::getCreateTime);

        IPage<Bill> billPage = billMapper.selectPage(new Page<>(page, size), wrapper);

        return billPage.convert(this::convertToVO);
    }

    @Override
    @Transactional
    public void pay(Long tenantId, Long billId) {
        Bill bill = billMapper.selectById(billId);
        if (bill == null) {
            throw new BusinessException("账单不存在");
        }
        if (!bill.getTenantId().equals(tenantId)) {
            throw new BusinessException("无权操作此账单");
        }
        if (bill.getStatus() != 0 && bill.getStatus() != 2) {
            throw new BusinessException("账单状态不正确");
        }

        // 检查余额
        User tenant = userMapper.selectById(tenantId);
        if (tenant.getBalance().compareTo(bill.getAmount()) < 0) {
            throw new BusinessException("余额不足，请先充值");
        }

        // 扣除租客余额
        tenant.setBalance(tenant.getBalance().subtract(bill.getAmount()));
        userMapper.updateById(tenant);

        // 增加房东余额
        User landlord = userMapper.selectById(bill.getLandlordId());
        landlord.setBalance(landlord.getBalance().add(bill.getAmount()));
        userMapper.updateById(landlord);

        // 更新账单状态
        bill.setStatus(1);
        bill.setPaidAmount(bill.getAmount());
        bill.setPaidTime(LocalDateTime.now());
        billMapper.updateById(bill);

        // 通知房东
        messageService.send(bill.getLandlordId(), "收到租金",
                "租客已支付租金 ¥" + bill.getAmount(), "bill");
    }

    @Override
    public Map<String, Object> getStatistics(Long userId, String role) {
        Map<String, Object> stats = new HashMap<>();

        LambdaQueryWrapper<Bill> wrapper = new LambdaQueryWrapper<>();
        if ("landlord".equals(role)) {
            wrapper.eq(Bill::getLandlordId, userId);
        } else {
            wrapper.eq(Bill::getTenantId, userId);
        }

        List<Bill> bills = billMapper.selectList(wrapper);

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal paidAmount = BigDecimal.ZERO;
        BigDecimal unpaidAmount = BigDecimal.ZERO;
        int unpaidCount = 0;

        for (Bill bill : bills) {
            totalAmount = totalAmount.add(bill.getAmount());
            if (bill.getStatus() == 1) {
                paidAmount = paidAmount.add(bill.getPaidAmount());
            } else {
                unpaidAmount = unpaidAmount.add(bill.getAmount());
                unpaidCount++;
            }
        }

        stats.put("totalAmount", totalAmount);
        stats.put("paidAmount", paidAmount);
        stats.put("unpaidAmount", unpaidAmount);
        stats.put("unpaidCount", unpaidCount);

        return stats;
    }

    private Map<String, Object> convertToVO(Bill bill) {
        Map<String, Object> vo = new HashMap<>();
        vo.put("id", bill.getId());
        vo.put("billNo", bill.getBillNo());
        vo.put("contractId", bill.getContractId());
        vo.put("amount", bill.getAmount());
        vo.put("billMonth", bill.getBillMonth());
        vo.put("dueDate", bill.getDueDate());
        vo.put("paidAmount", bill.getPaidAmount());
        vo.put("paidTime", bill.getPaidTime());
        vo.put("status", bill.getStatus());
        vo.put("createTime", bill.getCreateTime());

        // 获取合同和房源信息
        Contract contract = contractMapper.selectById(bill.getContractId());
        if (contract != null) {
            House house = houseMapper.selectById(contract.getHouseId());
            if (house != null) {
                Map<String, Object> houseVO = new HashMap<>();
                houseVO.put("id", house.getId());
                houseVO.put("title", house.getTitle());
                houseVO.put("address", house.getAddress());
                vo.put("house", houseVO);
            }
        }

        return vo;
    }
}
