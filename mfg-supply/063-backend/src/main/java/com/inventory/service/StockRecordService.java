package com.inventory.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inventory.common.PageResult;
import com.inventory.entity.Product;
import com.inventory.entity.StockRecord;
import com.inventory.entity.User;
import com.inventory.mapper.StockRecordMapper;
import com.inventory.mapper.UserMapper;
import com.inventory.vo.StockRecordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockRecordService {

    @Resource
    private StockRecordMapper stockRecordMapper;

    @Resource
    private ProductService productService;

    @Resource
    private UserMapper userMapper;

    public PageResult<StockRecordVO> page(Integer pageNum, Integer pageSize, String bizType, String bizNo, Long productId) {
        PageHelper.startPage(pageNum, pageSize);
        List<StockRecord> list = stockRecordMapper.selectPageList(bizType, bizNo, productId);
        PageInfo<StockRecord> pageInfo = new PageInfo<>(list);
        PageResult<StockRecordVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    private List<StockRecordVO> convertList(List<StockRecord> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<Product> products = productService.listAllForMap();
        List<User> users = userMapper.selectPageList(null, null, null);
        Map<Long, String> productMap = new HashMap<>();
        for (Product product : products) {
            productMap.put(product.getId(), product.getName());
        }
        Map<Long, String> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(), user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname());
        }
        List<StockRecordVO> result = new ArrayList<>();
        for (StockRecord item : list) {
            StockRecordVO vo = new StockRecordVO();
            BeanUtils.copyProperties(item, vo);
            vo.setProductName(productMap.getOrDefault(item.getProductId(), "未知商品"));
            vo.setOperatorName(userMap.getOrDefault(item.getOperatorId(), "未知用户"));
            result.add(vo);
        }
        return result;
    }
}
