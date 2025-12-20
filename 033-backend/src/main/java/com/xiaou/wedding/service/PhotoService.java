package com.xiaou.wedding.service;

import com.xiaou.wedding.entity.Photo;
import java.util.List;

public interface PhotoService {
    List<Photo> listByOrder(Long orderId);

    void upload(Photo photo);

    void update(Photo photo);
}
