package com.houpu.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.houpu.crowd.entity.Admin;

import java.util.List;

public interface AdminService {

    public void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String loginPwd);

    PageInfo<Admin> getPageInfo(String keyWord,Integer pageNum,Integer pageSize);

    void remove(Integer adminId);

    Admin getAdminById(Integer adminId);

    void edit(Admin admin);

    void saveAssignRole(Integer adminId, List<Integer> roleId);

    List<Admin> getAdminByName(String adminName);
}
