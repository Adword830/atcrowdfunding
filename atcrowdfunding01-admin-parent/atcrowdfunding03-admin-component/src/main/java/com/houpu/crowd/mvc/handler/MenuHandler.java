package com.houpu.crowd.mvc.handler;

import com.houpu.crowd.entity.Menu;
import com.houpu.crowd.service.api.MenuService;
import com.houpu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MenuHandler {

    @Autowired
    private MenuService menuService;

    /**
     * 储存整个树形结构进行返回给前台json
     * @return
     */
    // @ResponseBody
    @RequestMapping("/menu/get/whole/tree.json")
    public ResultEntity<Menu>  getWholeTreeNew(){
        // 1.查询到所有的菜单列表
        List<Menu> menuList = menuService.getAll();
        // 2.声明一个变量用来存储找到的根节点
        Menu root = null;
        // 3.对查询到的所有菜单列表进行遍历储存到map集合中方便记性判断pid和主键id
        Map<Integer,Menu> hashMap=new HashMap<>();
        for (Menu menuMap :menuList) {
            //
            Integer id = menuMap.getId();
            hashMap.put(id,menuMap);
        }
        // 4.再次循环遍历进行判断
        for (Menu menu :menuList) {
            // 获取到pid
            Integer pid = menu.getPid();
            // 5.如果当前节点为根节点
           if(pid==null){
               // 将其存入之前声明的变量中
               root=menu;
               // 跳过本次循环说明当前是根节点没有父节点
               continue;
           }
            // 在map集合中找到他的父级
            Menu father=hashMap.get(pid);
            // 在将其存入他的list集合之中
            father.getChildren().add(menu);
        }
        return ResultEntity.successWhitData(root);

    }

    /**
     * 新增的方法
     * @param menu
     * @return
     */
    // @ResponseBody
    @RequestMapping("/menu/save.json")
    public ResultEntity<String> saveMenu(Menu menu){
        menuService.saveMenu(menu);
        return ResultEntity.successWihtOutData();
    }

    // @ResponseBody
    @RequestMapping("/menu/edit.json")
    public ResultEntity<String> editMenu(Menu menu){
        menuService.editMenu(menu);
        return ResultEntity.successWihtOutData();
    }

    // @ResponseBody
    @RequestMapping("/menu/remove.json")
    public ResultEntity<String> editMenu(@RequestParam Integer id){
        menuService.removeMenu(id);
        return ResultEntity.successWihtOutData();
    }


}
