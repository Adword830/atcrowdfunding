package com.houpu.crowd.mvc.config;

import com.houpu.crowd.entity.Admin;
import com.houpu.crowd.entity.Role;
import com.houpu.crowd.service.api.AdminService;
import com.houpu.crowd.service.api.AuthService;
import com.houpu.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class CrowdUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    /**
     * Security使用此方法进行用户登陆查询数据库的方法
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // 查询当前所登陆的用户
        List<Admin> adminList = adminService.getAdminByName(userName);
        // 获取adminId
        Admin admin = adminList.get(0);
        Integer adminId = admin.getId();
        // 查询角色信息
        List<Role> assignRole = roleService.getAssignRole(adminId);
        // 查询权限信息
        List<String> authList = authService.getAuthNmeByAdminId(adminId);

        // 声明一个GrantedAuthority集合存放权限和角色信息
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role :assignRole) {
            String roleName="ROLE_"+role.getName();
            // 创建一个GrantedAuthority实现对象存放roleName
            SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(roleName);
            // 将角色信息添加到GrantedAuthority中
            authorities.add(simpleGrantedAuthority);
        }

        for (String authName: authList) {
            // 创建一个GrantedAuthority实现对象存放authName
            SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(authName);
            // 将权限信息添加到GrantedAuthority中
            authorities.add(simpleGrantedAuthority);
        }

        SecurityAdmin securityAdmin=new SecurityAdmin(admin,authorities);

        return securityAdmin;
    }
}
