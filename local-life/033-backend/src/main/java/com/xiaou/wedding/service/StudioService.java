package com.xiaou.wedding.service;

import com.xiaou.wedding.entity.Studio;
import java.util.List;

public interface StudioService {
    List<Studio> list();

    Studio detail(Long id);

    void create(Studio studio);

    void update(Studio studio);

    void delete(Long id);
}
