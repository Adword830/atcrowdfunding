package com.houpu.crowd.mvc.config;

import com.houpu.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * 继承User对象重写构造器
 */
public class SecurityAdmin extends User {

    private Admin admin;

    public SecurityAdmin(Admin admin, List<GrantedAuthority> authorities) {

        super(admin.getUserName(), admin.getUserPswd(), authorities);

        this.admin=admin;

        admin.setUserPswd(null);
    }

    public Admin getAdmin() {
        return admin;
    }
}
