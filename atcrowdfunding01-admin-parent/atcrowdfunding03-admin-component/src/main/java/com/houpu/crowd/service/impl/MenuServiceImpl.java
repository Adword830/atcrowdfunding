package com.houpu.crowd.service.impl;

import com.houpu.crowd.entity.Menu;
import com.houpu.crowd.entity.MenuExample;
import com.houpu.crowd.mapper.MenuMapper;
import com.houpu.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {

        MenuExample menuExample=new MenuExample();
        List<Menu> menus = menuMapper.selectByExample(menuExample);
        return menus;
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void editMenu(Menu menu) {
        // 有选择性的更新防止pid变成null出现问题
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void removeMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }

}
