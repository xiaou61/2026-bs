package com.xiaou.wedding.service;

import com.xiaou.wedding.entity.Photographer;
import java.util.List;

public interface PhotographerService {
    List<Photographer> list(String level);

    Photographer detail(Long id);

    void create(Photographer photographer);

    void update(Photographer photographer);

    void delete(Long id);
}
