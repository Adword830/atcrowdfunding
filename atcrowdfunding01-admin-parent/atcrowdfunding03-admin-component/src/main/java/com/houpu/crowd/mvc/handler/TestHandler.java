package com.houpu.crowd.mvc.handler;

import com.houpu.crowd.entity.Admin;
import com.houpu.crowd.service.api.AdminService;
import com.houpu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestHandler {

    @Autowired
    private AdminService adminService;


    @RequestMapping("/test.html")
    public String testSsm(Model model){
        List<Admin> adminList = adminService.getAll();
        model.addAttribute("list",adminList);
        System.out.println(10/0);
        return "trage";
    }

}
