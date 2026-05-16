package com.communityparty.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.communityparty.entity.PartyBranch;
import com.communityparty.mapper.PartyBranchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyBranchService {
    private final PartyBranchMapper mapper;

    public PageInfo<PartyBranch> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public void save(PartyBranch entity) {
        if (entity.getId() == null) mapper.insert(entity);
        else mapper.update(entity);
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        mapper.updateStatus(id, status);
    }
}
