package com.inventory.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inventory.common.BusinessException;
import com.inventory.common.PageResult;
import com.inventory.entity.Customer;
import com.inventory.entity.Product;
import com.inventory.entity.SaleOrder;
import com.inventory.entity.StockRecord;
import com.inventory.entity.User;
import com.inventory.mapper.CustomerMapper;
import com.inventory.mapper.SaleOrderMapper;
import com.inventory.mapper.StockRecordMapper;
import com.inventory.mapper.UserMapper;
import com.inventory.vo.SaleOrderVO;
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
public class SaleOrderService {

    @Resource
    private SaleOrderMapper saleOrderMapper;

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private ProductService productService;

    @Resource
    private StockRecordMapper stockRecordMapper;

    @Resource
    private UserMapper userMapper;

    public PageResult<SaleOrderVO> page(Integer pageNum, Integer pageSize, String orderNo, Long customerId, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<SaleOrder> list = saleOrderMapper.selectPageList(orderNo, customerId, status);
        PageInfo<SaleOrder> pageInfo = new PageInfo<>(list);
        PageResult<SaleOrderVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(SaleOrder order, Long operatorId) {
        validateBase(order);
        if (order.getId() == null) {
            order.setOrderNo("SO" + cn.hutool.core.util.IdUtil.getSnowflakeNextIdStr());
            order.setAmount(order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())));
            order.setStatus(0);
            order.setCreatorId(operatorId);
            order.setAuditTime(null);
            saleOrderMapper.insert(order);
        } else {
            SaleOrder db = saleOrderMapper.selectById(order.getId());
            if (db == null) {
                throw new BusinessException("销售单不存在");
            }
            if (db.getStatus() != null && db.getStatus() != 0) {
                throw new BusinessException("已审核销售单不允许修改");
            }
            order.setOrderNo(db.getOrderNo());
            order.setCreatorId(db.getCreatorId());
            order.setStatus(0);
            order.setAuditTime(null);
            order.setAmount(order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())));
            saleOrderMapper.update(order);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id, Long operatorId) {
        SaleOrder order = saleOrderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("销售单不存在");
        }
        if (order.getStatus() != null && order.getStatus() == 1) {
            throw new BusinessException("销售单已审核");
        }
        Product product = productService.mustGetById(order.getProductId());
        Integer before = product.getStock() == null ? 0 : product.getStock();
        if (before < order.getQuantity()) {
            throw new BusinessException("库存不足，当前库存：" + before);
        }
        Integer after = before - order.getQuantity();
        productService.updateStock(product.getId(), after);
        order.setStatus(1);
        order.setAuditTime(LocalDateTime.now());
        saleOrderMapper.update(order);

        StockRecord record = new StockRecord();
        record.setBizType("SALE");
        record.setBizNo(order.getOrderNo());
        record.setProductId(order.getProductId());
        record.setChangeQty(-order.getQuantity());
        record.setBeforeStock(before);
        record.setAfterStock(after);
        record.setRemark("销售出库");
        record.setOperatorId(operatorId);
        stockRecordMapper.insert(record);
    }

    public void deleteById(Long id) {
        SaleOrder db = saleOrderMapper.selectById(id);
        if (db != null && db.getStatus() != null && db.getStatus() == 1) {
            throw new BusinessException("已审核销售单不允许删除");
        }
        saleOrderMapper.deleteById(id);
    }

    public BigDecimal todayAmount() {
        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = start.plusDays(1);
        BigDecimal amount = saleOrderMapper.sumAuditAmount(start, end);
        return amount == null ? BigDecimal.ZERO : amount;
    }

    public List<Map<String, Object>> dailyAmount(LocalDateTime start, LocalDateTime end) {
        return saleOrderMapper.dailyAmount(start, end);
    }

    private void validateBase(SaleOrder order) {
        if (order == null) {
            throw new BusinessException("销售单参数不能为空");
        }
        if (order.getCustomerId() == null) {
            throw new BusinessException("请选择客户");
        }
        if (customerMapper.selectById(order.getCustomerId()) == null) {
            throw new BusinessException("客户不存在");
        }
        if (order.getProductId() == null) {
            throw new BusinessException("请选择商品");
        }
        productService.mustGetById(order.getProductId());
        if (order.getQuantity() == null || order.getQuantity() <= 0) {
            throw new BusinessException("销售数量必须大于0");
        }
        if (order.getPrice() == null || order.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("销售单价必须大于0");
        }
        if (order.getRemark() != null) {
            order.setRemark(order.getRemark().trim());
        }
    }

    private List<SaleOrderVO> convertList(List<SaleOrder> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<Customer> customers = customerMapper.selectPageList(null, null, null);
        List<User> users = userMapper.selectPageList(null, null, null);
        List<Product> products = productService.listAllForMap();
        Map<Long, String> customerMap = new HashMap<>();
        for (Customer customer : customers) {
            customerMap.put(customer.getId(), customer.getName());
        }
        Map<Long, String> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(), user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname());
        }
        Map<Long, String> productMap = new HashMap<>();
        for (Product product : products) {
            productMap.put(product.getId(), product.getName());
        }
        List<SaleOrderVO> result = new ArrayList<>();
        for (SaleOrder item : list) {
            SaleOrderVO vo = new SaleOrderVO();
            BeanUtils.copyProperties(item, vo);
            vo.setCustomerName(customerMap.getOrDefault(item.getCustomerId(), "未知客户"));
            vo.setCreatorName(userMap.getOrDefault(item.getCreatorId(), "未知用户"));
            vo.setProductName(productMap.getOrDefault(item.getProductId(), "未知商品"));
            result.add(vo);
        }
        return result;
    }
}
