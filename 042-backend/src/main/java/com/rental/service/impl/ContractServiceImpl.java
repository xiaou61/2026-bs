package com.rental.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rental.common.BusinessException;
import com.rental.entity.Contract;
import com.rental.entity.House;
import com.rental.entity.User;
import com.rental.mapper.ContractMapper;
import com.rental.mapper.HouseMapper;
import com.rental.mapper.UserMapper;
import com.rental.service.BillService;
import com.rental.service.ContractService;
import com.rental.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    @Lazy
    private BillService billService;

    @Override
    public void create(Long landlordId, Contract contract) {
        House house = houseMapper.selectById(contract.getHouseId());
        if (house == null) {
            throw new BusinessException("房源不存在");
        }
        if (!house.getLandlordId().equals(landlordId)) {
            throw new BusinessException("无权操作此房源");
        }

        // 生成合同编号
        contract.setContractNo("HT" + IdUtil.getSnowflakeNextIdStr());
        contract.setLandlordId(landlordId);
        contract.setStatus(0);

        contractMapper.insert(contract);

        // 通知租客签署合同
        messageService.send(contract.getTenantId(), "新合同待签署",
                "您有一份新的租赁合同待签署，请及时处理", "contract");
    }

    @Override
    public IPage<Map<String, Object>> getList(Long userId, String role, int page, int size, Integer status) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();

        if ("landlord".equals(role)) {
            wrapper.eq(Contract::getLandlordId, userId);
        } else {
            wrapper.eq(Contract::getTenantId, userId);
        }

        if (status != null) {
            wrapper.eq(Contract::getStatus, status);
        }

        wrapper.orderByDesc(Contract::getCreateTime);

        IPage<Contract> contractPage = contractMapper.selectPage(new Page<>(page, size), wrapper);

        return contractPage.convert(this::convertToVO);
    }

    @Override
    public Map<String, Object> getDetail(Long userId, Long contractId) {
        Contract contract = contractMapper.selectById(contractId);
        if (contract == null) {
            throw new BusinessException("合同不存在");
        }

        // 检查权限
        if (!contract.getLandlordId().equals(userId) && !contract.getTenantId().equals(userId)) {
            throw new BusinessException("无权查看此合同");
        }

        return convertToVO(contract);
    }

    @Override
    @Transactional
    public void sign(Long tenantId, Long contractId) {
        Contract contract = contractMapper.selectById(contractId);
        if (contract == null) {
            throw new BusinessException("合同不存在");
        }
        if (!contract.getTenantId().equals(tenantId)) {
            throw new BusinessException("无权操作此合同");
        }
        if (contract.getStatus() != 0) {
            throw new BusinessException("合同状态不正确");
        }

        contract.setStatus(1);
        contract.setSignTime(LocalDateTime.now());
        contractMapper.updateById(contract);

        // 更新房源状态为已出租
        House house = houseMapper.selectById(contract.getHouseId());
        if (house != null) {
            house.setStatus(3);
            houseMapper.updateById(house);
        }

        // 生成首期账单
        billService.generateFirstBill(contract);

        // 通知房东
        messageService.send(contract.getLandlordId(), "合同已签署",
                "租客已签署合同，合同编号：" + contract.getContractNo(), "contract");
    }

    @Override
    public void terminate(Long userId, Long contractId) {
        Contract contract = contractMapper.selectById(contractId);
        if (contract == null) {
            throw new BusinessException("合同不存在");
        }
        if (!contract.getLandlordId().equals(userId) && !contract.getTenantId().equals(userId)) {
            throw new BusinessException("无权操作此合同");
        }
        if (contract.getStatus() != 1) {
            throw new BusinessException("合同状态不正确");
        }

        contract.setStatus(3);
        contractMapper.updateById(contract);

        // 更新房源状态为已上架
        House house = houseMapper.selectById(contract.getHouseId());
        if (house != null) {
            house.setStatus(1);
            houseMapper.updateById(house);
        }

        // 通知另一方
        Long notifyUserId = contract.getLandlordId().equals(userId) 
                ? contract.getTenantId() : contract.getLandlordId();
        messageService.send(notifyUserId, "合同已终止",
                "合同已被终止，合同编号：" + contract.getContractNo(), "contract");
    }

    private Map<String, Object> convertToVO(Contract contract) {
        Map<String, Object> vo = new HashMap<>();
        vo.put("id", contract.getId());
        vo.put("contractNo", contract.getContractNo());
        vo.put("houseId", contract.getHouseId());
        vo.put("startDate", contract.getStartDate());
        vo.put("endDate", contract.getEndDate());
        vo.put("monthlyRent", contract.getMonthlyRent());
        vo.put("deposit", contract.getDeposit());
        vo.put("paymentDay", contract.getPaymentDay());
        vo.put("paymentMethod", contract.getPaymentMethod());
        vo.put("terms", contract.getTerms());
        vo.put("status", contract.getStatus());
        vo.put("signTime", contract.getSignTime());
        vo.put("createTime", contract.getCreateTime());

        // 获取房源信息
        House house = houseMapper.selectById(contract.getHouseId());
        if (house != null) {
            Map<String, Object> houseVO = new HashMap<>();
            houseVO.put("id", house.getId());
            houseVO.put("title", house.getTitle());
            houseVO.put("address", house.getAddress());
            houseVO.put("images", house.getImages());
            vo.put("house", houseVO);
        }

        // 获取租客信息
        User tenant = userMapper.selectById(contract.getTenantId());
        if (tenant != null) {
            Map<String, Object> tenantVO = new HashMap<>();
            tenantVO.put("id", tenant.getId());
            tenantVO.put("realName", tenant.getRealName());
            tenantVO.put("phone", tenant.getPhone());
            vo.put("tenant", tenantVO);
        }

        // 获取房东信息
        User landlord = userMapper.selectById(contract.getLandlordId());
        if (landlord != null) {
            Map<String, Object> landlordVO = new HashMap<>();
            landlordVO.put("id", landlord.getId());
            landlordVO.put("realName", landlord.getRealName());
            landlordVO.put("phone", landlord.getPhone());
            vo.put("landlord", landlordVO);
        }

        return vo;
    }
}
