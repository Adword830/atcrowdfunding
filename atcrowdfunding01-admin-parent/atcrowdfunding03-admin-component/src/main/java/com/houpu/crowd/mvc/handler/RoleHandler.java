package com.houpu.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.houpu.crowd.entity.Role;
import com.houpu.crowd.service.api.RoleService;
import com.houpu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RoleHandler {
    @Autowired
    private RoleService roleService;

    /**
     * 进行分页的关键字查询不带关键字也通过此查询方法
     * @param pageNum
     * @param pageSize
     * @param keyWord
     * @return
     */
    // @ResponseBody
    @PreAuthorize("hasRole('部长')")
    @RequestMapping("/role/get/page.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                                                    @RequestParam String keyWord){

        PageInfo<Role> pageInfo = roleService.getPageInfo(keyWord, pageNum, pageSize);

        return ResultEntity.successWhitData(pageInfo);

    }

    /**
     * 实现新增方法
     * @param roleName
     */
    // @ResponseBody
    @RequestMapping("/role/do/add.json")
    public ResultEntity<Role> addRole(@RequestParam String roleName){

        roleService.save(new Role(roleName));
        return ResultEntity.successWihtOutData();
    }

    /**
     * 更新的方法
     * @param role
     * @return
     */
    // @ResponseBody
    @RequestMapping("/role/do/edit.json")
    public ResultEntity<Role> editRole(Role role){

        roleService.updateRole(role);
        return ResultEntity.successWihtOutData();
    }

    // @ResponseBody
    @RequestMapping("/role/do/remove.json")
    public ResultEntity<String> remove(@RequestBody List<Integer> roleId){

        roleService.removeById(roleId);
        return ResultEntity.successWihtOutData();
    }

}
