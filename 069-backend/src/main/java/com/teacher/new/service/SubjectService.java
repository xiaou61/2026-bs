package com.teacher.new.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teacher.new.common.BusinessException;
import com.teacher.new.common.PageResult;
import com.teacher.new.entity.SubjectInfo;
import com.teacher.new.mapper.SubjectInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    @Resource
    private SubjectInfoMapper subjectInfoMapper;

    public PageResult<SubjectInfo> page(Integer pageNum, Integer pageSize, String subjectName, String subjectCode, Integer status) {
        Page<SubjectInfo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SubjectInfo> wrapper = new LambdaQueryWrapper<SubjectInfo>()
                .like(StringUtils.hasText(subjectName), SubjectInfo::getSubjectName, subjectName == null ? null : subjectName.trim())
                .like(StringUtils.hasText(subjectCode), SubjectInfo::getSubjectCode, subjectCode == null ? null : subjectCode.trim())
                .eq(status != null, SubjectInfo::getStatus, status)
                .orderByAsc(SubjectInfo::getId);
        Page<SubjectInfo> resultPage = subjectInfoMapper.selectPage(page, wrapper);
        PageResult<SubjectInfo> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<SubjectInfo> list() {
        return subjectInfoMapper.selectList(new LambdaQueryWrapper<SubjectInfo>()
                .eq(SubjectInfo::getStatus, 1)
                .orderByAsc(SubjectInfo::getId));
    }

    public void save(SubjectInfo subject) {
        if (subject == null || !StringUtils.hasText(subject.getSubjectName()) || !StringUtils.hasText(subject.getSubjectCode())) {
            throw new BusinessException("科目参数不完整");
        }
        if (subject.getId() == null) {
            add(subject);
        } else {
            update(subject);
        }
    }

    private void add(SubjectInfo subject) {
        String code = subject.getSubjectCode().trim();
        if (subjectInfoMapper.selectOne(new LambdaQueryWrapper<SubjectInfo>().eq(SubjectInfo::getSubjectCode, code).last("limit 1")) != null) {
            throw new BusinessException("科目编码已存在");
        }
        subject.setSubjectName(subject.getSubjectName().trim());
        subject.setSubjectCode(code);
        subject.setStatus(subject.getStatus() == null ? 1 : (subject.getStatus() == 0 ? 0 : 1));
        subjectInfoMapper.insert(subject);
    }

    private void update(SubjectInfo subject) {
        SubjectInfo db = subjectInfoMapper.selectById(subject.getId());
        if (db == null) {
            throw new BusinessException("科目不存在");
        }
        SubjectInfo exist = subjectInfoMapper.selectOne(new LambdaQueryWrapper<SubjectInfo>()
                .eq(SubjectInfo::getSubjectCode, subject.getSubjectCode().trim())
                .last("limit 1"));
        if (exist != null && !exist.getId().equals(subject.getId())) {
            throw new BusinessException("科目编码已存在");
        }
        db.setSubjectName(subject.getSubjectName().trim());
        db.setSubjectCode(subject.getSubjectCode().trim());
        if (subject.getStatus() != null) {
            db.setStatus(subject.getStatus() == 0 ? 0 : 1);
        }
        subjectInfoMapper.updateById(db);
    }

    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("状态不合法");
        }
        SubjectInfo db = subjectInfoMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("科目不存在");
        }
        db.setStatus(status);
        subjectInfoMapper.updateById(db);
    }

    public void deleteById(Long id) {
        subjectInfoMapper.deleteById(id);
    }

    public Long countAll() {
        return subjectInfoMapper.selectCount(null);
    }

    public Map<Long, SubjectInfo> mapByIds(Collection<Long> ids) {
        Map<Long, SubjectInfo> map = new HashMap<>();
        if (ids == null || ids.isEmpty()) {
            return map;
        }
        Set<Long> idSet = ids.stream().filter(java.util.Objects::nonNull).collect(Collectors.toSet());
        if (idSet.isEmpty()) {
            return map;
        }
        List<SubjectInfo> list = subjectInfoMapper.selectBatchIds(idSet);
        for (SubjectInfo item : list) {
            map.put(item.getId(), item);
        }
        return map;
    }
}
