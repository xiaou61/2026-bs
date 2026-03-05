package com.charity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.charity.entity.Child;
import com.charity.entity.Donation;
import com.charity.entity.Donor;
import com.charity.entity.Project;
import com.charity.mapper.ChildMapper;
import com.charity.mapper.DonationMapper;
import com.charity.mapper.DonorMapper;
import com.charity.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private ChildMapper childMapper;

    @Autowired
    private DonorMapper donorMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private DonationMapper donationMapper;

    public Map<String, Object> getDashboard() {
        Map<String, Object> result = new HashMap<>();
        result.put("totalChildren", childMapper.selectCount(null));
        QueryWrapper<Child> childWrapper = new QueryWrapper<>();
        childWrapper.eq("sponsor_status", 1);
        result.put("sponsoredChildren", childMapper.selectCount(childWrapper));
        result.put("totalDonors", donorMapper.selectCount(null));
        List<Donation> donations = donationMapper.selectList(null);
        BigDecimal totalAmount = donations.stream()
                .map(Donation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        result.put("totalAmount", totalAmount);
        result.put("totalProjects", projectMapper.selectCount(null));
        return result;
    }

    public Map<String, Object> getDonationTrend(LocalDate startDate, LocalDate endDate) {
        QueryWrapper<Donation> wrapper = new QueryWrapper<>();
        if (startDate != null) {
            wrapper.ge("donation_date", startDate);
        }
        if (endDate != null) {
            wrapper.le("donation_date", endDate);
        }
        wrapper.orderByAsc("donation_date");
        List<Donation> donations = donationMapper.selectList(wrapper);
        List<String> dateList = new ArrayList<>();
        List<BigDecimal> amountList = new ArrayList<>();
        for (Donation donation : donations) {
            dateList.add(donation.getDonationDate().toString());
            amountList.add(donation.getAmount());
        }
        Map<String, Object> result = new HashMap<>();
        result.put("dateList", dateList);
        result.put("amountList", amountList);
        return result;
    }

    public Map<String, Object> getRegionDistribution() {
        List<Child> children = childMapper.selectList(null);
        Map<String, Integer> provinceMap = new HashMap<>();
        for (Child child : children) {
            String province = child.getProvince();
            provinceMap.put(province, provinceMap.getOrDefault(province, 0) + 1);
        }
        List<String> provinceList = new ArrayList<>(provinceMap.keySet());
        List<Integer> countList = new ArrayList<>(provinceMap.values());
        Map<String, Object> result = new HashMap<>();
        result.put("provinceList", provinceList);
        result.put("countList", countList);
        return result;
    }
}
