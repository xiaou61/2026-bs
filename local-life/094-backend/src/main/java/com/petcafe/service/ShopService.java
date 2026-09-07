package com.petcafe.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petcafe.common.BusinessException;
import com.petcafe.common.PageResult;
import com.petcafe.entity.CafeArea;
import com.petcafe.entity.CafeShop;
import com.petcafe.entity.ResidentPet;
import com.petcafe.entity.SeatInfo;
import com.petcafe.mapper.CafeAreaMapper;
import com.petcafe.mapper.CafeShopMapper;
import com.petcafe.mapper.ResidentPetMapper;
import com.petcafe.mapper.SeatInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShopService {

    @Resource
    private CafeShopMapper shopMapper;

    @Resource
    private CafeAreaMapper areaMapper;

    @Resource
    private SeatInfoMapper seatMapper;

    @Resource
    private ResidentPetMapper petMapper;

    public PageResult<CafeShop> page(Integer pageNum, Integer pageSize, String name, String status, Long areaId) {
        Page<CafeShop> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CafeShop> wrapper = new LambdaQueryWrapper<CafeShop>()
                .like(StringUtils.hasText(name), CafeShop::getName, name == null ? null : name.trim())
                .eq(StringUtils.hasText(status), CafeShop::getStatus, status == null ? null : status.trim().toUpperCase())
                .eq(areaId != null, CafeShop::getAreaId, areaId)
                .orderByDesc(CafeShop::getId);
        Page<CafeShop> resultPage = shopMapper.selectPage(page, wrapper);
        fillAreaName(resultPage.getRecords());
        PageResult<CafeShop> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<CafeShop> listAll() {
        List<CafeShop> list = shopMapper.selectList(new LambdaQueryWrapper<CafeShop>().orderByDesc(CafeShop::getId));
        fillAreaName(list);
        return list;
    }

    public List<CafeShop> publicList() {
        List<CafeShop> list = shopMapper.selectList(new LambdaQueryWrapper<CafeShop>()
                .eq(CafeShop::getStatus, "OPEN")
                .orderByDesc(CafeShop::getId));
        fillAreaName(list);
        return list;
    }

    public CafeShop getById(Long id) {
        CafeShop shop = shopMapper.selectById(id);
        if (shop == null) {
            throw new BusinessException("门店不存在");
        }
        fillAreaName(java.util.Collections.singletonList(shop));
        return shop;
    }

    public void save(CafeShop shop) {
        if (shop == null || !StringUtils.hasText(shop.getShopNo()) || !StringUtils.hasText(shop.getName()) || shop.getAreaId() == null) {
            throw new BusinessException("门店信息不完整");
        }
        CafeArea area = areaMapper.selectById(shop.getAreaId());
        if (area == null) {
            throw new BusinessException("区域不存在");
        }
        validateShopNo(shop);
        if (shop.getId() == null) {
            shop.setStatus(StringUtils.hasText(shop.getStatus()) ? shop.getStatus().trim().toUpperCase() : "OPEN");
            shop.setScore(shop.getScore() == null ? 5 : shop.getScore());
            shop.setPerCapita(shop.getPerCapita() == null ? BigDecimal.ZERO : shop.getPerCapita());
            shopMapper.insert(shop);
            return;
        }
        CafeShop db = getById(shop.getId());
        db.setShopNo(shop.getShopNo().trim());
        db.setAreaId(shop.getAreaId());
        db.setName(shop.getName().trim());
        db.setTheme(StringUtils.hasText(shop.getTheme()) ? shop.getTheme().trim() : null);
        db.setOpenTime(StringUtils.hasText(shop.getOpenTime()) ? shop.getOpenTime().trim() : null);
        db.setCloseTime(StringUtils.hasText(shop.getCloseTime()) ? shop.getCloseTime().trim() : null);
        db.setStatus(StringUtils.hasText(shop.getStatus()) ? shop.getStatus().trim().toUpperCase() : db.getStatus());
        db.setCover(StringUtils.hasText(shop.getCover()) ? shop.getCover().trim() : null);
        db.setManagerName(StringUtils.hasText(shop.getManagerName()) ? shop.getManagerName().trim() : null);
        db.setManagerPhone(StringUtils.hasText(shop.getManagerPhone()) ? shop.getManagerPhone().trim() : null);
        db.setScore(shop.getScore() == null ? db.getScore() : shop.getScore());
        db.setPerCapita(shop.getPerCapita() == null ? db.getPerCapita() : shop.getPerCapita());
        db.setRemark(StringUtils.hasText(shop.getRemark()) ? shop.getRemark().trim() : null);
        shopMapper.updateById(db);
    }

    public void deleteById(Long id) {
        if (seatMapper.selectCount(new LambdaQueryWrapper<SeatInfo>().eq(SeatInfo::getShopId, id)) > 0) {
            throw new BusinessException("该门店下存在座位，无法删除");
        }
        if (petMapper.selectCount(new LambdaQueryWrapper<ResidentPet>().eq(ResidentPet::getShopId, id)) > 0) {
            throw new BusinessException("该门店下存在店宠，无法删除");
        }
        shopMapper.deleteById(id);
    }

    public Long countAll() {
        return shopMapper.selectCount(null);
    }

    private void validateShopNo(CafeShop shop) {
        CafeShop exist = shopMapper.selectOne(new LambdaQueryWrapper<CafeShop>()
                .eq(CafeShop::getShopNo, shop.getShopNo().trim())
                .last("limit 1"));
        if (exist != null && (shop.getId() == null || !exist.getId().equals(shop.getId()))) {
            throw new BusinessException("门店编号已存在");
        }
    }

    private void fillAreaName(List<CafeShop> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<Long> areaIds = list.stream().map(CafeShop::getAreaId).distinct().collect(Collectors.toList());
        Map<Long, String> areaMap = areaMapper.selectList(new LambdaQueryWrapper<CafeArea>().in(CafeArea::getId, areaIds))
                .stream()
                .collect(Collectors.toMap(CafeArea::getId, CafeArea::getName, (a, b) -> a, HashMap::new));
        list.forEach(item -> item.setAreaName(areaMap.get(item.getAreaId())));
    }
}
