package com.houpu.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.houpu.crowd.entity.Role;

import java.util.List;

public interface RoleService {

    PageInfo<Role> getPageInfo(String keyWord,Integer pageNum, Integer pageSize);

    void save(Role role);

    void updateRole(Role role);

    void removeById(List<Integer> roleId);

    List<Role> getAssignRole(Integer adminId);

    List<Role> getUnAssignRole(Integer adminId);
}
