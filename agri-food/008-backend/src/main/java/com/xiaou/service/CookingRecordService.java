package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.dto.CookingRecordDTO;
import com.xiaou.entity.CookingRecord;
import com.xiaou.vo.CookingRecordVO;

import java.util.List;
import java.util.Map;

public interface CookingRecordService extends IService<CookingRecord> {
    
    void createRecord(Long userId, CookingRecordDTO dto);
    
    List<CookingRecordVO> getRecordList(Long userId);
    
    Map<String, Object> getStats(Long userId);
}

