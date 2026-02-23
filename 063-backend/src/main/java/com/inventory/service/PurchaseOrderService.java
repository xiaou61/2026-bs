package com.inventory.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inventory.common.BusinessException;
import com.inventory.common.PageResult;
import com.inventory.entity.Product;
import com.inventory.entity.PurchaseOrder;
import com.inventory.entity.StockRecord;
import com.inventory.entity.Supplier;
import com.inventory.entity.User;
import com.inventory.mapper.PurchaseOrderMapper;
import com.inventory.mapper.StockRecordMapper;
import com.inventory.mapper.SupplierMapper;
import com.inventory.mapper.UserMapper;
import com.inventory.vo.PurchaseOrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseOrderService {

    @Resource
    private PurchaseOrderMapper purchaseOrderMapper;

    @Resource
    private SupplierMapper supplierMapper;

    @Resource
    private ProductService productService;

    @Resource
    private StockRecordMapper stockRecordMapper;

    @Resource
    private UserMapper userMapper;

    public PageResult<PurchaseOrderVO> page(Integer pageNum, Integer pageSize, String orderNo, Long supplierId, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<PurchaseOrder> list = purchaseOrderMapper.selectPageList(orderNo, supplierId, status);
        PageInfo<PurchaseOrder> pageInfo = new PageInfo<>(list);
        PageResult<PurchaseOrderVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(PurchaseOrder order, Long operatorId) {
        validateBase(order);
        if (order.getId() == null) {
            order.setOrderNo("PO" + cn.hutool.core.util.IdUtil.getSnowflakeNextIdStr());
            order.setAmount(order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())));
            order.setStatus(order.getStatus() == null ? 0 : order.getStatus());
            if (order.getStatus() != 0) {
                order.setStatus(0);
            }
            order.setCreatorId(operatorId);
            order.setAuditTime(null);
            purchaseOrderMapper.insert(order);
        } else {
            PurchaseOrder db = purchaseOrderMapper.selectById(order.getId());
            if (db == null) {
                throw new BusinessException("采购单不存在");
            }
            if (db.getStatus() != null && db.getStatus() != 0) {
                throw new BusinessException("已审核采购单不允许修改");
            }
            order.setOrderNo(db.getOrderNo());
            order.setCreatorId(db.getCreatorId());
            order.setStatus(0);
            order.setAuditTime(null);
            order.setAmount(order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())));
            purchaseOrderMapper.update(order);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id, Long operatorId) {
        PurchaseOrder order = purchaseOrderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("采购单不存在");
        }
        if (order.getStatus() != null && order.getStatus() == 1) {
            throw new BusinessException("采购单已审核");
        }
        Product product = productService.mustGetById(order.getProductId());
        Integer before = product.getStock() == null ? 0 : product.getStock();
        Integer after = before + order.getQuantity();
        productService.updateStock(product.getId(), after);
        order.setStatus(1);
        order.setAuditTime(LocalDateTime.now());
        purchaseOrderMapper.update(order);

        StockRecord record = new StockRecord();
        record.setBizType("PURCHASE");
        record.setBizNo(order.getOrderNo());
        record.setProductId(order.getProductId());
        record.setChangeQty(order.getQuantity());
        record.setBeforeStock(before);
        record.setAfterStock(after);
        record.setRemark("采购入库");
        record.setOperatorId(operatorId);
        stockRecordMapper.insert(record);
    }

    public void deleteById(Long id) {
        PurchaseOrder db = purchaseOrderMapper.selectById(id);
        if (db != null && db.getStatus() != null && db.getStatus() == 1) {
            throw new BusinessException("已审核采购单不允许删除");
        }
        purchaseOrderMapper.deleteById(id);
    }

    public BigDecimal todayAmount() {
        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = start.plusDays(1);
        BigDecimal amount = purchaseOrderMapper.sumAuditAmount(start, end);
        return amount == null ? BigDecimal.ZERO : amount;
    }

    public List<Map<String, Object>> dailyAmount(LocalDateTime start, LocalDateTime end) {
        return purchaseOrderMapper.dailyAmount(start, end);
    }

    private void validateBase(PurchaseOrder order) {
        if (order == null) {
            throw new BusinessException("采购单参数不能为空");
        }
        if (order.getSupplierId() == null) {
            throw new BusinessException("请选择供应商");
        }
        if (supplierMapper.selectById(order.getSupplierId()) == null) {
            throw new BusinessException("供应商不存在");
        }
        if (order.getProductId() == null) {
            throw new BusinessException("请选择商品");
        }
        productService.mustGetById(order.getProductId());
        if (order.getQuantity() == null || order.getQuantity() <= 0) {
            throw new BusinessException("采购数量必须大于0");
        }
        if (order.getPrice() == null || order.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("采购单价必须大于0");
        }
        if (order.getRemark() != null) {
            order.setRemark(order.getRemark().trim());
        }
    }

    private List<PurchaseOrderVO> convertList(List<PurchaseOrder> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<Supplier> suppliers = supplierMapper.selectPageList(null, null, null);
        List<User> users = userMapper.selectPageList(null, null, null);
        List<com.inventory.entity.Product> products = productService.listAllForMap();
        Map<Long, String> supplierMap = new HashMap<>();
        for (Supplier supplier : suppliers) {
            supplierMap.put(supplier.getId(), supplier.getName());
        }
        Map<Long, String> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(), user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname());
        }
        Map<Long, String> productMap = new HashMap<>();
        for (com.inventory.entity.Product product : products) {
            productMap.put(product.getId(), product.getName());
        }
        List<PurchaseOrderVO> result = new ArrayList<>();
        for (PurchaseOrder item : list) {
            PurchaseOrderVO vo = new PurchaseOrderVO();
            BeanUtils.copyProperties(item, vo);
            vo.setSupplierName(supplierMap.getOrDefault(item.getSupplierId(), "未知供应商"));
            vo.setCreatorName(userMap.getOrDefault(item.getCreatorId(), "未知用户"));
            vo.setProductName(productMap.getOrDefault(item.getProductId(), "未知商品"));
            result.add(vo);
        }
        return result;
    }
}
