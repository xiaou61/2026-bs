package com.xiaou.wedding.service.impl;

import com.xiaou.wedding.entity.Photo;
import com.xiaou.wedding.exception.BusinessException;
import com.xiaou.wedding.mapper.PhotoMapper;
import com.xiaou.wedding.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoMapper photoMapper;

    @Override
    public List<Photo> listByOrder(Long orderId) {
        return photoMapper.listByOrder(orderId);
    }

    @Override
    public void upload(Photo photo) {
        photo.setUploadTime(photo.getUploadTime() == null ? LocalDateTime.now() : photo.getUploadTime());
        photoMapper.insert(photo);
    }

    @Override
    public void update(Photo photo) {
        if (photo.getId() == null) {
            throw new BusinessException("Photo id missing");
        }
        photoMapper.update(photo);
    }
}
