package com.groupbuy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.groupbuy.common.BusinessException;
import com.groupbuy.entity.Category;
import com.groupbuy.entity.Merchant;
import com.groupbuy.entity.Product;
import com.groupbuy.mapper.CategoryMapper;
import com.groupbuy.mapper.MerchantMapper;
import com.groupbuy.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    public Page<Product> page(Integer pageNum, Integer pageSize, Long categoryId, String name, Integer status, Long merchantId) {
        Page<Product> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        if (categoryId != null) {
            wrapper.eq("category_id", categoryId);
        }
        if (name != null && !name.isEmpty()) {
            wrapper.like("name", name);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (merchantId != null) {
            wrapper.eq("merchant_id", merchantId);
        }
        wrapper.orderByDesc("create_time");
        Page<Product> result = productMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::fillProductInfo);
        return result;
    }

    public Product detail(Long id) {
        Product product = productMapper.selectById(id);
        if (product != null) {
            fillProductInfo(product);
        }
        return product;
    }

    public void add(Long merchantId, Product product) {
        product.setMerchantId(merchantId);
        product.setStatus(1);
        product.setSales(0);
        productMapper.insert(product);
    }

    public void update(Long merchantId, Product product) {
        Product old = requireProduct(product.getId());
        ensureMerchantOwner(old, merchantId);
        product.setMerchantId(old.getMerchantId());
        productMapper.updateById(product);
    }

    public void updateStatus(Long merchantId, Long id, Integer status) {
        Product old = requireProduct(id);
        ensureMerchantOwner(old, merchantId);
        Product product = new Product();
        product.setId(id);
        product.setStatus(status);
        productMapper.updateById(product);
    }

    public void delete(Long merchantId, Long id) {
        Product old = requireProduct(id);
        ensureMerchantOwner(old, merchantId);
        productMapper.deleteById(id);
    }

    private void fillProductInfo(Product product) {
        Merchant merchant = merchantMapper.selectById(product.getMerchantId());
        if (merchant != null) {
            product.setMerchantName(merchant.getName());
        }
        Category category = categoryMapper.selectById(product.getCategoryId());
        if (category != null) {
            product.setCategoryName(category.getName());
        }
    }

    public void updateSales(Long id, Integer quantity) {
        Product product = productMapper.selectById(id);
        product.setSales(product.getSales() + quantity);
        product.setStock(product.getStock() - quantity);
        productMapper.updateById(product);
    }

    private Product requireProduct(Long id) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            throw new BusinessException(404, "商品不存在");
        }
        return product;
    }

    private void ensureMerchantOwner(Product product, Long merchantId) {
        if (merchantId == null || !merchantId.equals(product.getMerchantId())) {
            throw new BusinessException(403, "无权操作该商品");
        }
    }
}
