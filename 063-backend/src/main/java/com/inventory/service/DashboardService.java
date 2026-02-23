package com.inventory.service;

import com.inventory.mapper.CustomerMapper;
import com.inventory.mapper.ProductMapper;
import com.inventory.mapper.SupplierMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Resource
    private SupplierMapper supplierMapper;

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private PurchaseOrderService purchaseOrderService;

    @Resource
    private SaleOrderService saleOrderService;

    public Map<String, Object> stats() {
        Map<String, Object> map = new HashMap<>();
        map.put("supplierCount", supplierMapper.countAll());
        map.put("customerCount", customerMapper.countAll());
        map.put("productCount", productMapper.countAll());
        map.put("warnCount", productMapper.countWarnProducts());
        map.put("todayPurchaseAmount", purchaseOrderService.todayAmount());
        map.put("todaySaleAmount", saleOrderService.todayAmount());
        return map;
    }

    public List<Map<String, Object>> trend() {
        LocalDate startDay = LocalDate.now().minusDays(6);
        LocalDateTime start = startDay.atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();
        List<Map<String, Object>> purchaseList = purchaseOrderService.dailyAmount(start, end);
        List<Map<String, Object>> saleList = saleOrderService.dailyAmount(start, end);
        Map<String, BigDecimal> purchaseMap = new HashMap<>();
        for (Map<String, Object> row : purchaseList) {
            purchaseMap.put(row.get("day").toString(), new BigDecimal(row.get("amount").toString()));
        }
        Map<String, BigDecimal> saleMap = new HashMap<>();
        for (Map<String, Object> row : saleList) {
            saleMap.put(row.get("day").toString(), new BigDecimal(row.get("amount").toString()));
        }
        Map<String, Map<String, Object>> merged = new LinkedHashMap<>();
        for (int i = 6; i >= 0; i--) {
            String day = LocalDate.now().minusDays(i).toString();
            Map<String, Object> item = new HashMap<>();
            item.put("day", day);
            item.put("purchaseAmount", purchaseMap.getOrDefault(day, BigDecimal.ZERO));
            item.put("saleAmount", saleMap.getOrDefault(day, BigDecimal.ZERO));
            merged.put(day, item);
        }
        return new ArrayList<>(merged.values());
    }
}
