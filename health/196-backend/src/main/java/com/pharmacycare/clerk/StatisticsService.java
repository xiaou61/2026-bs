package com.pharmacycare.clerk;

import com.pharmacycare.mapper.PharmacyStoreMapper;
import com.pharmacycare.mapper.CustomerProfileMapper;
import com.pharmacycare.mapper.MedicineCatalogMapper;
import com.pharmacycare.mapper.PrescriptionRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final PharmacyStoreMapper pharmacyStoreMapper;
    private final CustomerProfileMapper customerProfileMapper;
    private final MedicineCatalogMapper medicineCatalogMapper;
    private final PrescriptionRecordMapper prescriptionRecordMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("storeCount", pharmacyStoreMapper.countAll());
        data.put("customerCount", customerProfileMapper.countAll());
        data.put("medicineCount", medicineCatalogMapper.countAll());
        data.put("prescriptionCount", prescriptionRecordMapper.countAll());
        data.put("trend", Arrays.asList(31, 46, 58, 72, 86, 101, 119));
        data.put("pie", Arrays.asList(map("已登记", 36), map("审核中", 24), map("待复核", 18), map("已购药", 32), map("已提醒", 28)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
