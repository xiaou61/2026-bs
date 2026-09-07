package com.xiaou.sport.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.sport.entity.SportTrack;
import com.xiaou.sport.mapper.SportTrackMapper;
import com.xiaou.sport.service.SportTrackService;
import org.springframework.stereotype.Service;

@Service
public class SportTrackServiceImpl extends ServiceImpl<SportTrackMapper, SportTrack> implements SportTrackService {
}
