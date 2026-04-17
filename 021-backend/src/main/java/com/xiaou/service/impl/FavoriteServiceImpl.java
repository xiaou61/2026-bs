package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.entity.Category;
import com.xiaou.entity.Favorite;
import com.xiaou.entity.Product;
import com.xiaou.entity.User;
import com.xiaou.exception.BusinessException;
import com.xiaou.mapper.CategoryMapper;
import com.xiaou.mapper.FavoriteMapper;
import com.xiaou.mapper.ProductMapper;
import com.xiaou.mapper.UserMapper;
import com.xiaou.service.FavoriteService;
import com.xiaou.vo.FavoriteVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteMapper favoriteMapper;
    private final ProductMapper productMapper;
    private final UserMapper userMapper;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public void addFavorite(Long userId, Long productId) {
        Product product = productMapper.selectById(productId);
        if (product == null || product.getDeleted() == 1) {
            throw new BusinessException("商品不存在");
        }

        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId).eq(Favorite::getProductId, productId);
        if (favoriteMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("商品已收藏");
        }

        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setProductId(productId);
        favoriteMapper.insert(favorite);

        product.setFavoriteCount(product.getFavoriteCount() + 1);
        productMapper.updateById(product);
    }

    @Override
    @Transactional
    public void removeFavorite(Long userId, Long productId) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId).eq(Favorite::getProductId, productId);
        Favorite favorite = favoriteMapper.selectOne(wrapper);
        if (favorite == null) {
            return;
        }

        favoriteMapper.deleteById(favorite.getId());

        Product product = productMapper.selectById(productId);
        if (product != null && product.getFavoriteCount() > 0) {
            product.setFavoriteCount(product.getFavoriteCount() - 1);
            productMapper.updateById(product);
        }
    }

    @Override
    public Page<FavoriteVO> getMyFavorites(Long userId, Integer current, Integer size) {
        Page<Favorite> page = new Page<>(current, size);
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId).orderByDesc(Favorite::getCreateTime);
        Page<Favorite> favoritePage = favoriteMapper.selectPage(page, wrapper);

        Page<FavoriteVO> result = new Page<>(current, size, favoritePage.getTotal());
        result.setRecords(favoritePage.getRecords().stream().map(this::toFavoriteVO).toList());
        return result;
    }

    @Override
    public boolean checkFavorite(Long userId, Long productId) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId).eq(Favorite::getProductId, productId);
        return favoriteMapper.selectCount(wrapper) > 0;
    }

    private FavoriteVO toFavoriteVO(Favorite favorite) {
        Product product = productMapper.selectById(favorite.getProductId());
        if (product == null) {
            throw new BusinessException("收藏商品不存在");
        }
        User seller = userMapper.selectById(product.getSellerId());
        Category category = categoryMapper.selectById(product.getCategoryId());

        FavoriteVO favoriteVO = new FavoriteVO();
        favoriteVO.setProductId(product.getId());
        favoriteVO.setSellerId(product.getSellerId());
        favoriteVO.setCategoryId(product.getCategoryId());
        favoriteVO.setTitle(product.getTitle());
        favoriteVO.setDescription(product.getDescription());
        favoriteVO.setPrice(product.getPrice());
        favoriteVO.setOriginalPrice(product.getOriginalPrice());
        favoriteVO.setCondition(product.getCondition());
        favoriteVO.setImages(product.getImages());
        favoriteVO.setStatus(product.getStatus());
        favoriteVO.setViewCount(product.getViewCount());
        favoriteVO.setFavoriteCount(product.getFavoriteCount());
        favoriteVO.setSellerName(seller != null ? seller.getRealName() : "未知卖家");
        favoriteVO.setSellerAvatar(seller != null ? seller.getAvatar() : null);
        favoriteVO.setSellerCreditScore(seller != null ? seller.getCreditScore() : 0);
        favoriteVO.setCategoryName(category != null ? category.getCategoryName() : null);
        favoriteVO.setFavoriteTime(favorite.getCreateTime());
        return favoriteVO;
    }
}
