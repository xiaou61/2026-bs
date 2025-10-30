package com.xiaou.sport.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.sport.entity.VenueBooking;
import com.xiaou.sport.mapper.VenueBookingMapper;
import com.xiaou.sport.service.VenueBookingService;
import org.springframework.stereotype.Service;

@Service
public class VenueBookingServiceImpl extends ServiceImpl<VenueBookingMapper, VenueBooking>
        implements VenueBookingService {
}
