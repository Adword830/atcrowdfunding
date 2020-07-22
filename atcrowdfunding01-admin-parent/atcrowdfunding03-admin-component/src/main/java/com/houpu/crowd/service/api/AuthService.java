package com.houpu.crowd.service.api;

import com.houpu.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {
    List<Auth> getAll();

    List<Integer> getAssignRoleAuthIdByRoleId(Integer roleId);

    void saveAssignRoleAuth(Map<String, List<Integer>> map);

    List<String> getAuthNmeByAdminId(Integer adminid);
}
