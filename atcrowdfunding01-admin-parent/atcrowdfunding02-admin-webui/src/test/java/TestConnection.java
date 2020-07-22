import com.github.pagehelper.PageInfo;
import com.houpu.crowd.entity.*;
import com.houpu.crowd.mapper.AuthMapper;
import com.houpu.crowd.mapper.MenuMapper;
import com.houpu.crowd.mapper.RoleMapper;
import com.houpu.crowd.mvc.handler.MenuHandler;
import com.houpu.crowd.service.api.AdminService;
import com.houpu.crowd.service.api.MenuService;
import com.houpu.crowd.service.api.RoleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class TestConnection {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    AuthMapper authMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void getAll() {
        AuthExample authExample=new AuthExample();
        List<Auth> authList=authMapper.selectByExample(authExample);
        System.out.println(authList.toString());
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
    @Test
    public void addAdmin() throws SQLException {
        for(int i=0;i<200;i++){
            adminService.saveAdmin(new Admin(null,"xly"+i,"123456","谢璐瑶","xly@qq.com","2020-6-18"));
        }

    }
    @Test
    public void addRole() throws SQLException {
        for(int i=0;i<200;i++){
            roleService.save(new Role(null,"role"+i));
        }

    }
    @Test
    public void testPageInfoAdmin(){
        Logger logger = LoggerFactory.getLogger(TestConnection.class);

        PageInfo<Admin> pageInfo = adminService.getPageInfo("", 1, 5);
        logger.info("分页:{}", pageInfo);
    }
    @Test
    public void testPageInfoRole(){
        Logger logger = LoggerFactory.getLogger(TestConnection.class);

        PageInfo<Role> pageInfo = roleService.getPageInfo("", 1, 5);
        logger.info("分页:{}", pageInfo.getList().size());
    }
    @Test
    public void testMenu() throws Exception {
        // 1.查询到所有的菜单列表
        List<Menu> menuList = menuService.getAll();
        // 2.声明一个变量用来存储找到的根节点
        Menu root = null;
        // 3.对查询到的所有菜单列表进行遍历储存到map集合中方便记性判断pid和主键id
        Map<Integer,Menu> hashMap=new HashMap<Integer,Menu>();
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
        Logger logger = LoggerFactory.getLogger(MenuHandler.class);
        logger.info("root:{}",root);
    }
    @Test
    public void test01(){
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }

}
