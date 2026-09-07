package com.xiaou.sport.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.sport.entity.Venue;
import com.xiaou.sport.mapper.VenueMapper;
import com.xiaou.sport.service.VenueService;
import org.springframework.stereotype.Service;

@Service
public class VenueServiceImpl extends ServiceImpl<VenueMapper, Venue> implements VenueService {
}
