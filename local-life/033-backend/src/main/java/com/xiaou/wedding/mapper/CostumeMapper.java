package com.xiaou.wedding.mapper;

import com.xiaou.wedding.entity.Costume;
import com.xiaou.wedding.entity.CostumeBorrow;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface CostumeMapper {
    Costume findById(@Param("id") Long id);

    List<Costume> list(@Param("category") String category, @Param("status") String status);

    int insert(Costume costume);

    int update(Costume costume);

    int logicDelete(@Param("id") Long id);

    int insertBorrow(CostumeBorrow borrow);

    int updateBorrowReturn(@Param("id") Long id, @Param("returnDate") java.time.LocalDateTime returnDate, @Param("status") String status);

    List<CostumeBorrow> borrowList(@Param("costumeId") Long costumeId, @Param("status") String status);
}
