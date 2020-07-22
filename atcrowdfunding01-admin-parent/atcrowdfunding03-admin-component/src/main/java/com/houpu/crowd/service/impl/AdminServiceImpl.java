package com.houpu.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.houpu.crowd.entity.Admin;
import com.houpu.crowd.entity.AdminExample;
import com.houpu.crowd.exception.LoginFailedException;
import com.houpu.crowd.mapper.AdminMapper;
import com.houpu.crowd.service.api.AdminService;
import com.houpu.crowd.util.CrowdUtil;
import com.houpu.crowd.util.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveAdmin(Admin admin) {
        String userPswd = bCryptPasswordEncoder.encode(admin.getUserPswd());
        admin.setUserPswd(userPswd);
        adminMapper.insert(admin);
    }

    public List<Admin> getAll() {
        List<Admin> adminList = adminMapper.selectByExample(new AdminExample());
        return adminList;
    }

    public Admin getAdminByLoginAcct(String loginAcct, String loginPwd) {
        // 1.通过用户名查询用户
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        // 2.判断当前用户是否存在
        if(admins == null || admins.size() == 0){
            throw  new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 3.内部错误
        if(admins.size()>1){
            throw new LoginFailedException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        // 3.判断当前用户是否存在
        Admin admin = admins.get(0);
        if(admin == null){
            throw  new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 4.判断当前user对象的密码是否正确
        String userPswdDB = admin.getUserPswd();
        // 5.对明文面进行加密
        String loginPwdFrom = CrowdUtil.md5(loginPwd);
        // 6.判断当前密码是否正确
        if(!Objects.equals(userPswdDB,loginPwdFrom)){
            throw  new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 7.密码正确
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyWord, Integer pageNum, Integer pageSize) {
        // 1.开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 2.根据关键字进行查询
        List<Admin> list = adminMapper.selectAdminByKeyWord(keyWord);
        // 3.将查询出来的结果放到pageInfo对象里面
        PageInfo pageInfo=new PageInfo(list);
        // 4.返回pageInfo对象
        return pageInfo;
    }

    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        return admin;
    }

    @Override
    public void edit(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public void saveAssignRole(Integer adminId, List<Integer> roleId) {
        // 把数据库里面原来用户的数据进行删除
        adminMapper.deleteByAdminId(adminId);
        // 保存用户现在传入的数据
        if(roleId!=null&&roleId.size()>0){
            adminMapper.insertAssignRole(adminId,roleId);
        }

    }

    @Override
    public List<Admin> getAdminByName(String adminName) {
        AdminExample adminExample=new AdminExample();

        AdminExample.Criteria criteria = adminExample.createCriteria();

        criteria.andLoginAcctEqualTo(adminName);

        return adminMapper.selectByExample(adminExample);
    }
}
