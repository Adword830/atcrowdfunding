package com.houpu.crowd.mvc.handler;

import com.houpu.crowd.entity.Auth;
import com.houpu.crowd.entity.Role;
import com.houpu.crowd.service.api.AdminService;
import com.houpu.crowd.service.api.AuthService;
import com.houpu.crowd.service.api.RoleService;
import com.houpu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class AssignHandler {

    @Autowired
    AdminService adminService;

    @Autowired
    RoleService roleService;

    @Autowired
    AuthService authService;

    /**
     * 去到角色分配的页面
     * @param adminId
     * @param modelMap
     * @return
     */
    @RequestMapping("/assign/to/assign/role/page.html")
    public String toAssignRolePage(@RequestParam Integer adminId, ModelMap modelMap){

        // 查询当前用户已经分配的角色
        List<Role> assignRoleList=roleService.getAssignRole(adminId);

        // 查询未分配的角色
        List<Role> unAssignRoleList=roleService.getUnAssignRole(adminId);

        modelMap.addAttribute("assignRoleList",assignRoleList);

        modelMap.addAttribute("unAssignRoleList",unAssignRoleList);

        return "assign-role";
    }

    /**
     * 保存角色分配
     * @param adminId
     * @param keyWord
     * @param pageNum
     * @param roleId
     * @return
     */
    @RequestMapping("/assign/do/role/assign.html")
    public String  saveAssignRole(@RequestParam Integer adminId,
                                  @RequestParam String keyWord,
                                  @RequestParam Integer pageNum,
                                  @RequestParam(required = false) List<Integer> roleId
    ){
        adminService.saveAssignRole(adminId,roleId);
        return "redirect:/admin/get/page.html?keyWord="+keyWord+"&pageNum"+pageNum;
    }

    /**
     * 查询到所有的权限
     * @return
     */
    @ResponseBody
    @RequestMapping("/assign/role/auth.json")
    public ResultEntity<List<Auth>> getAuth(){
        List<Auth> authList=authService.getAll();
        return ResultEntity.successWhitData(authList);
    }
    @ResponseBody
    @RequestMapping("/assign/role/auth/role/id.json")
    public ResultEntity<List<Integer>> getAssignRoleAuthIdByRoleId(@RequestParam Integer roleId){
        List<Integer> authIdList=authService.getAssignRoleAuthIdByRoleId(roleId);
        return  ResultEntity.successWhitData(authIdList);
    }
    @ResponseBody
    @RequestMapping("/assign/role/auth/save.json")
    public ResultEntity<String> saveAssignRoleAuth(@RequestBody Map<String,List<Integer>> map){

        authService.saveAssignRoleAuth(map);

        return ResultEntity.successWihtOutData();
    }

}
