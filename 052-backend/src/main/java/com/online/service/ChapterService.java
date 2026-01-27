package com.online.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.entity.Chapter;
import com.online.mapper.ChapterMapper;
import org.springframework.stereotype.Service;

@Service
public class ChapterService extends ServiceImpl<ChapterMapper, Chapter> {
}
