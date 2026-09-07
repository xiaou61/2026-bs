package com.xiaou.wedding.service;

import com.xiaou.wedding.entity.Costume;
import com.xiaou.wedding.entity.CostumeBorrow;
import java.util.List;

public interface CostumeService {
    List<Costume> list(String category, String status);

    Costume detail(Long id);

    void create(Costume costume);

    void update(Costume costume);

    void delete(Long id);

    void borrow(CostumeBorrow borrow);

    void returnCostume(Long borrowId);

    List<CostumeBorrow> borrowList(Long costumeId, String status);
}
