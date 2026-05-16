package com.bookdrift.clerk;

import com.bookdrift.mapper.DriftShelfMapper;
import com.bookdrift.mapper.BookProfileMapper;
import com.bookdrift.mapper.ReaderProfileMapper;
import com.bookdrift.mapper.BookDonationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final DriftShelfMapper driftShelfMapper;
    private final BookProfileMapper bookProfileMapper;
    private final ReaderProfileMapper readerProfileMapper;
    private final BookDonationMapper bookDonationMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("shelfCount", driftShelfMapper.countAll());
        data.put("bookCount", bookProfileMapper.countAll());
        data.put("readerCount", readerProfileMapper.countAll());
        data.put("donationCount", bookDonationMapper.countAll());
        data.put("trend", Arrays.asList(58, 74, 88, 105, 121, 137, 154));
        data.put("pie", Arrays.asList(map("漂流中", 31), map("已借阅", 44), map("已打卡", 19), map("已授章", 68)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
