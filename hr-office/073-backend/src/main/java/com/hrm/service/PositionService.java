package com.hrm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrm.common.BusinessException;
import com.hrm.entity.Position;
import com.hrm.mapper.EmployeeMapper;
import com.hrm.mapper.PositionMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class PositionService {
    @Resource
    private PositionMapper positionMapper;
    @Resource
    private EmployeeMapper employeeMapper;

    public Position getById(Long id) {
        return positionMapper.selectById(id);
    }

    public PageInfo<Position> getList(String name, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(positionMapper.selectList(name, status));
    }

    public List<Position> getAll() {
        return positionMapper.selectAll();
    }

    public void add(Position position) {
        position.setStatus(1);
        positionMapper.insert(position);
    }

    public void update(Position position) {
        positionMapper.update(position);
    }

    public void delete(Long id) {
        if (employeeMapper.countByPositionId(id) > 0) {
            throw new BusinessException("该职位下存在员工，无法删除");
        }
        positionMapper.deleteById(id);
    }
}
