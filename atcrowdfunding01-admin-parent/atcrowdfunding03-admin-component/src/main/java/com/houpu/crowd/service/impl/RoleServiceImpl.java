package com.houpu.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.houpu.crowd.entity.Role;
import com.houpu.crowd.entity.RoleExample;
import com.houpu.crowd.mapper.RoleMapper;
import com.houpu.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(String keyWord,Integer pageNum, Integer pageSize) {
        //startPage方法第一个参数传入你想要查询的页码，第二个参数传入你想要查询的记录条数
        PageHelper.startPage(pageNum,pageSize);
        List<Role> list = roleMapper.selectByKeyWord(keyWord);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @Override
    public void save(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void removeById(List<Integer> roleId) {
        RoleExample roleExample=new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(roleId);

        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public List<Role> getAssignRole(Integer adminId) {

        return roleMapper.selectAssignRole(adminId);
    }

    @Override
    public List<Role> getUnAssignRole(Integer adminId) {
        return roleMapper.selectUnAssignRole(adminId);
    }
}
