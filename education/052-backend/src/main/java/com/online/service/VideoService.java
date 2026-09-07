package com.online.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.entity.Video;
import com.online.mapper.VideoMapper;
import org.springframework.stereotype.Service;

@Service
public class VideoService extends ServiceImpl<VideoMapper, Video> {
}
