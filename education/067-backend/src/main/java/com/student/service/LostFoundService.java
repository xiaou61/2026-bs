package com.student.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.student.common.BusinessException;
import com.student.common.PageResult;
import com.student.entity.LostFound;
import com.student.entity.User;
import com.student.mapper.LostFoundMapper;
import com.student.mapper.UserMapper;
import com.student.vo.LostFoundVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LostFoundService {

    @Resource
    private LostFoundMapper lostFoundMapper;

    @Resource
    private UserMapper userMapper;

    public PageResult<LostFoundVO> page(Integer pageNum, Integer pageSize, String keyword, Integer type, Integer status, Long publisherId) {
        PageHelper.startPage(pageNum, pageSize);
        List<LostFound> list = lostFoundMapper.selectPageList(keyword, type, status, publisherId);
        PageInfo<LostFound> pageInfo = new PageInfo<>(list);
        PageResult<LostFoundVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public void save(LostFound lostFound, String role, Long userId) {
        if (lostFound == null) {
            throw new BusinessException("失物参数不能为空");
        }
        if (lostFound.getType() == null || (lostFound.getType() != 1 && lostFound.getType() != 2)) {
            throw new BusinessException("类型不合法");
        }
        if (lostFound.getTitle() == null || lostFound.getTitle().trim().isEmpty()) {
            throw new BusinessException("标题不能为空");
        }
        if (lostFound.getItemName() == null || lostFound.getItemName().trim().isEmpty()) {
            throw new BusinessException("物品名称不能为空");
        }
        if (lostFound.getContact() == null || lostFound.getContact().trim().isEmpty()) {
            throw new BusinessException("联系方式不能为空");
        }
        lostFound.setTitle(lostFound.getTitle().trim());
        lostFound.setItemName(lostFound.getItemName().trim());
        lostFound.setContact(lostFound.getContact().trim());
        lostFound.setLocation(lostFound.getLocation() == null ? "" : lostFound.getLocation().trim());
        lostFound.setDescription(lostFound.getDescription() == null ? "" : lostFound.getDescription().trim());
        lostFound.setImageUrl(lostFound.getImageUrl() == null ? "" : lostFound.getImageUrl().trim());
        lostFound.setStatus(normalizeStatus(lostFound.getStatus(), 1));
        if (lostFound.getId() == null) {
            lostFound.setPublisherId(userId);
            lostFoundMapper.insert(lostFound);
        } else {
            LostFound db = lostFoundMapper.selectById(lostFound.getId());
            if (db == null) {
                throw new BusinessException("记录不存在");
            }
            if (!"ADMIN".equals(role) && !userId.equals(db.getPublisherId())) {
                throw new BusinessException("无权限修改该记录");
            }
            lostFound.setPublisherId(db.getPublisherId());
            lostFoundMapper.update(lostFound);
        }
    }

    public void updateStatus(Long id, Integer status, String role, Long userId) {
        if (status == null || (status != 1 && status != 2)) {
            throw new BusinessException("状态不合法");
        }
        LostFound db = lostFoundMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("记录不存在");
        }
        if (!"ADMIN".equals(role) && !userId.equals(db.getPublisherId())) {
            throw new BusinessException("无权限操作");
        }
        lostFoundMapper.updateStatus(id, status);
    }

    public void deleteById(Long id, String role, Long userId) {
        LostFound db = lostFoundMapper.selectById(id);
        if (db == null) {
            return;
        }
        if (!"ADMIN".equals(role) && !userId.equals(db.getPublisherId())) {
            throw new BusinessException("无权限删除该记录");
        }
        lostFoundMapper.deleteById(id);
    }

    public Long countAll() {
        Long count = lostFoundMapper.countAll();
        return count == null ? 0L : count;
    }

    private Integer normalizeStatus(Integer status, Integer fallback) {
        Integer s = status == null ? fallback : status;
        if (s == null) {
            s = 1;
        }
        if (s != 1 && s != 2) {
            throw new BusinessException("状态不合法");
        }
        return s;
    }

    private List<LostFoundVO> convertList(List<LostFound> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Map<Long, String> userMap = buildUserMap();
        List<LostFoundVO> result = new ArrayList<>();
        for (LostFound item : list) {
            LostFoundVO vo = new LostFoundVO();
            BeanUtils.copyProperties(item, vo);
            vo.setPublisherName(userMap.getOrDefault(item.getPublisherId(), ""));
            result.add(vo);
        }
        return result;
    }

    private Map<Long, String> buildUserMap() {
        List<User> users = userMapper.selectPageList(null, null, null, null);
        Map<Long, String> userMap = new HashMap<>();
        for (User user : users) {
            String name = user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname();
            userMap.put(user.getId(), name);
        }
        return userMap;
    }
}
