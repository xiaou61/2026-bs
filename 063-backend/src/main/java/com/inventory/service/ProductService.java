package com.inventory.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inventory.common.BusinessException;
import com.inventory.common.PageResult;
import com.inventory.entity.Category;
import com.inventory.entity.Product;
import com.inventory.mapper.CategoryMapper;
import com.inventory.mapper.ProductMapper;
import com.inventory.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private CategoryMapper categoryMapper;

    public PageResult<ProductVO> page(Integer pageNum, Integer pageSize, String name, String productNo, Long categoryId, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> list = productMapper.selectPageList(name, productNo, categoryId, status);
        PageInfo<Product> pageInfo = new PageInfo<>(list);
        PageResult<ProductVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public List<Product> list() {
        return productMapper.selectEnabledList();
    }

    public List<Product> listAllForMap() {
        return productMapper.selectPageList(null, null, null, null);
    }

    public void save(Product product) {
        if (product == null || product.getName() == null || product.getName().trim().isEmpty()) {
            throw new BusinessException("商品名称不能为空");
        }
        if (product.getCategoryId() == null) {
            throw new BusinessException("请选择商品分类");
        }
        categoryMustExist(product.getCategoryId());
        product.setName(product.getName().trim());
        if (product.getSpec() != null) {
            product.setSpec(product.getSpec().trim());
        }
        if (product.getUnit() != null) {
            product.setUnit(product.getUnit().trim());
        }
        if (product.getCostPrice() == null) {
            product.setCostPrice(BigDecimal.ZERO);
        }
        if (product.getSalePrice() == null) {
            product.setSalePrice(BigDecimal.ZERO);
        }
        if (product.getStock() == null) {
            product.setStock(0);
        }
        if (product.getStockWarn() == null) {
            product.setStockWarn(10);
        }
        if (product.getStatus() == null) {
            product.setStatus(1);
        }
        if (product.getId() == null) {
            product.setProductNo("PROD" + IdUtil.getSnowflakeNextIdStr());
            productMapper.insert(product);
        } else {
            Product db = productMapper.selectById(product.getId());
            if (db == null) {
                throw new BusinessException("商品不存在");
            }
            if (product.getProductNo() == null || product.getProductNo().trim().isEmpty()) {
                product.setProductNo(db.getProductNo());
            } else {
                product.setProductNo(product.getProductNo().trim());
            }
            productMapper.update(product);
        }
    }

    public void deleteById(Long id) {
        productMapper.deleteById(id);
    }

    public Product mustGetById(Long id) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        return product;
    }

    public void updateStock(Long id, Integer stock) {
        productMapper.updateStock(id, stock);
    }

    public Long countAll() {
        Long count = productMapper.countAll();
        return count == null ? 0L : count;
    }

    public Long countWarnProducts() {
        Long count = productMapper.countWarnProducts();
        return count == null ? 0L : count;
    }

    private List<ProductVO> convertList(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return new ArrayList<>();
        }
        List<Category> categories = categoryMapper.selectPageList(null, null);
        Map<Long, String> categoryMap = new HashMap<>();
        for (Category category : categories) {
            categoryMap.put(category.getId(), category.getName());
        }
        List<ProductVO> result = new ArrayList<>();
        for (Product product : products) {
            ProductVO vo = new ProductVO();
            BeanUtils.copyProperties(product, vo);
            vo.setCategoryName(categoryMap.getOrDefault(product.getCategoryId(), "未知分类"));
            result.add(vo);
        }
        return result;
    }

    private void categoryMustExist(Long categoryId) {
        if (categoryMapper.selectById(categoryId) == null) {
            throw new BusinessException("商品分类不存在");
        }
    }
}
