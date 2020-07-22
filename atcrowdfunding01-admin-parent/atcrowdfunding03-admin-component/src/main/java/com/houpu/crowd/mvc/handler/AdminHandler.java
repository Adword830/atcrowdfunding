package com.houpu.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.houpu.crowd.entity.Admin;
import com.houpu.crowd.exception.LoginAccectAlreadyInUseException;
import com.houpu.crowd.exception.LoginAccectAlreadyInUseUpdateException;
import com.houpu.crowd.service.api.AdminService;
import com.houpu.crowd.util.CrowdUtil;
import com.houpu.crowd.util.constant.CrowdConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    /**
     * 用户登录的方法handler
     * @param loginAcct
     * @param loginPwd
     * @return
     */
    /*@RequestMapping("/admin/do/login.html")
    public String loginAdminHandler(@RequestParam String loginAcct, @RequestParam String loginPwd,
                                    HttpSession session){
        // 1.调用service方法
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, loginPwd);
        // 2.admin对象
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_NAME,admin);
        // 3.返回后台主界面
        return "redirect:/admin/to/main/page.html";
    }*/

    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping("/admin/do/loginOut.html")
    public String loginOutHandler(HttpSession session){
        // 1.强制消除session
        session.invalidate();

        return "redirect:/admin/to/main/login/page.html";
    }

    /**
     * 查询用户所有数据或者查询所需要关键字的用户数据
     * @param keyWord
     * @param pageNum
     * @param pageSize
     * @param modelMap
     * @return
     */
    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(@RequestParam(value = "keyWord" ,defaultValue = "") String keyWord,
                                    @RequestParam(value = "pageNum" ,defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize" ,defaultValue = "5") Integer pageSize,
                                    ModelMap modelMap){
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyWord, pageNum, pageSize);

        modelMap.addAttribute(CrowdConstant.ATTR_NAEM_PAGE_INFO,pageInfo);

        return "admin-page";
    }

    /**
     * 删除记录的方法
     * @param adminId
     * @param pageNum
     * @param keyWord
     * @return
     */
    @RequestMapping("admin/do/remove/{adminId}/{pageNum}/{keyWord}.html")
    public String remove(@PathVariable("adminId") Integer adminId,
                         @PathVariable("pageNum") Integer pageNum,
                         @PathVariable("keyWord") String keyWord){

        adminService.remove(adminId);

        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyWord="+keyWord;
    }

    /**
     * 添加方法
     * @param admin
     * @return
     */
    @PostAuthorize("hasAuthority('user:add')")
    @RequestMapping("/admin/do/add.html")
    public String add(Admin admin){
        String userPwd_md5 = CrowdUtil.md5(admin.getUserPswd());
        admin.setUserPswd(userPwd_md5);
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd hh:mm:ss");
        String format = simpleDateFormat.format(date);
        admin.setCreateTime(format);

        try {
            adminService.saveAdmin(admin);
        }catch (Exception e){
            if(e instanceof DuplicateKeyException){
                throw  new LoginAccectAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALRERDAY_IN_USE);
            }
        }


        return "redirect:/admin/get/page.html?pageNum="+Integer.MAX_VALUE;
    }

    /**
     * 修改进行表单回显的操作
     * @param adminId
     * @param modelMap
     * @return
     */
    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(@RequestParam Integer adminId,ModelMap modelMap){
        Admin admin = adminService.getAdminById(adminId);
        modelMap.addAttribute("admin",admin);
        return "admin-edit";
    }

   @RequestMapping("/admin/do/edit.html")
    public String edit(
            @RequestParam String keyWord,
            @RequestParam Integer pageNum,
            Admin admin){

       try {
           LoggerFactory.getLogger(AdminHandler.class).info("admin : {}",admin);
           adminService.edit(admin);
       }catch (Exception e){
           if(e instanceof DuplicateKeyException){
               LoggerFactory.getLogger(AdminHandler.class).info("出现异常");
               throw  new LoginAccectAlreadyInUseUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALRERDAY_IN_USE);
           }
       }
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyWord="+keyWord;
   }

}
