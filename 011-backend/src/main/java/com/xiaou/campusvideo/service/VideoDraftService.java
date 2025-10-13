package com.xiaou.campusvideo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusvideo.entity.VideoDraft;
import com.xiaou.campusvideo.mapper.VideoDraftMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoDraftService extends ServiceImpl<VideoDraftMapper, VideoDraft> {
    
    public void saveDraft(VideoDraft draft) {
        if (draft.getId() != null) {
            this.updateById(draft);
        } else {
            this.save(draft);
        }
    }
    
    public List<VideoDraft> getDraftList(Long userId) {
        LambdaQueryWrapper<VideoDraft> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoDraft::getUserId, userId)
               .orderByDesc(VideoDraft::getUpdateTime);
        return this.list(wrapper);
    }
}

