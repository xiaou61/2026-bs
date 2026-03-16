package com.railway.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.railway.common.BusinessException;
import com.railway.common.PageResult;
import com.railway.entity.Train;
import com.railway.mapper.TrainMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TrainService {

    @Resource
    private TrainMapper trainMapper;

    public PageResult<Train> page(Integer pageNum, Integer pageSize, String trainCode, String trainType, Integer status) {
        Page<Train> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Train> wrapper = new LambdaQueryWrapper<Train>()
                .like(StringUtils.hasText(trainCode), Train::getTrainCode, trim(trainCode))
                .like(StringUtils.hasText(trainType), Train::getTrainType, trim(trainType))
                .eq(status != null, Train::getStatus, status)
                .orderByAsc(Train::getTrainCode);
        Page<Train> resultPage = trainMapper.selectPage(page, wrapper);
        PageResult<Train> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<Train> enabledList() {
        return trainMapper.selectList(new LambdaQueryWrapper<Train>()
                .eq(Train::getStatus, 1)
                .orderByAsc(Train::getTrainCode));
    }

    public Train getById(Long id) {
        Train train = trainMapper.selectById(id);
        if (train == null) {
            throw new BusinessException("列车不存在");
        }
        return train;
    }

    public void save(Train train) {
        validate(train);
        if (train.getId() == null) {
            assertUniqueCode(train.getTrainCode(), null);
            if (train.getStatus() == null) {
                train.setStatus(1);
            }
            train.setTrainCode(train.getTrainCode().trim());
            train.setTrainName(train.getTrainName().trim());
            train.setTrainType(train.getTrainType().trim());
            train.setCoverUrl(trim(train.getCoverUrl()));
            train.setDescription(trim(train.getDescription()));
            trainMapper.insert(train);
            return;
        }
        Train db = getById(train.getId());
        assertUniqueCode(train.getTrainCode(), db.getId());
        db.setTrainCode(train.getTrainCode().trim());
        db.setTrainName(train.getTrainName().trim());
        db.setTrainType(train.getTrainType().trim());
        db.setCarriageCount(train.getCarriageCount());
        db.setSeatCount(train.getSeatCount());
        db.setCoverUrl(trim(train.getCoverUrl()));
        db.setDescription(trim(train.getDescription()));
        db.setStatus(train.getStatus() == null ? db.getStatus() : train.getStatus());
        trainMapper.updateById(db);
    }

    public void deleteById(Long id) {
        trainMapper.deleteById(id);
    }

    public Long countAll() {
        return trainMapper.selectCount(new LambdaQueryWrapper<Train>().eq(Train::getStatus, 1));
    }

    private void validate(Train train) {
        if (train == null || !StringUtils.hasText(train.getTrainCode()) || !StringUtils.hasText(train.getTrainName())) {
            throw new BusinessException("列车编码和名称不能为空");
        }
        if (!StringUtils.hasText(train.getTrainType())) {
            throw new BusinessException("列车类型不能为空");
        }
        if (train.getCarriageCount() == null || train.getCarriageCount() <= 0) {
            throw new BusinessException("车厢数量必须大于0");
        }
        if (train.getSeatCount() == null || train.getSeatCount() <= 0) {
            throw new BusinessException("座席数量必须大于0");
        }
    }

    private void assertUniqueCode(String trainCode, Long excludeId) {
        Train exist = trainMapper.selectOne(new LambdaQueryWrapper<Train>()
                .eq(Train::getTrainCode, trainCode.trim())
                .last("limit 1"));
        if (exist != null && (excludeId == null || !exist.getId().equals(excludeId))) {
            throw new BusinessException("列车编码已存在");
        }
    }

    private String trim(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }
}
