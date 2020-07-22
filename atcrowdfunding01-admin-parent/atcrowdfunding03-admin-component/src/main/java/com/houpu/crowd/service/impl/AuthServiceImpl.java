package com.houpu.crowd.service.impl;

import com.houpu.crowd.entity.Auth;
import com.houpu.crowd.entity.AuthExample;
import com.houpu.crowd.mapper.AuthMapper;
import com.houpu.crowd.mapper.RoleMapper;
import com.houpu.crowd.service.api.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        AuthExample authExample=new AuthExample();
        List<Auth> authList=authMapper.selectByExample(authExample);
        return authList;
    }

    @Override
    public List<Integer> getAssignRoleAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAuthIdByRoleId(roleId);
    }

    @Override
    public void saveAssignRoleAuth(Map<String, List<Integer>> map) {
        // 获取roleId
        List<Integer> list = map.get("roleId");
        Integer roleId=list.get(0);

        // 删除旧的权限
        authMapper.deleteByRoleId(roleId);
        // 获取adminId的集合
        List<Integer> authId = map.get("authId");
        if(authId!=null && authId.size()>0){
            // 把新的权限插入
            authMapper.insertAuthId(roleId,authId);
        }

    }

    @Override
    public List<String> getAuthNmeByAdminId(Integer adminid) {
        return  authMapper.selectAuthNmeByAdminId(adminid);
    }
}
