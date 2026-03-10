package com.teachres.mapper;

import com.teachres.entity.MaterialDownload;

import java.util.List;
import java.util.Map;

public interface MaterialDownloadMapper {
    int insert(MaterialDownload materialDownload);
    long countAll();
    List<Map<String, Object>> selectMonthTrend();
    List<Map<String, Object>> selectHotTop5();
}
