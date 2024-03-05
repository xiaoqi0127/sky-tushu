package com.itmoli;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmoli.controller.admin.AdminController;
import com.itmoli.dto.AdminLoginDto;
import com.itmoli.dto.UserPageDto;
import com.itmoli.po.Admin;
import com.itmoli.po.User;
import com.itmoli.mapper.AdminMapper;
import com.itmoli.query.Thread;
import com.itmoli.properties.JwtProperties;
import com.itmoli.service.AdminService;
import com.itmoli.service.UserService;
import com.itmoli.utils.JwtCreate;
import com.itmoli.vo.PageVo;
import com.itmoli.vo.Result;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class ServerApplicationTests {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private JwtCreate jwtCreate;
    @Autowired
    private AdminController adminController;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @Test
    void test1() {
        System.out.println(jwtProperties);

        Admin admin = new Admin();
        admin.setId(1);
        admin.setAccount("admin");

        String jwt = jwtCreate.getJwt(admin);
        System.out.println(jwt);

    }

    @Test
    void test2() {

        AdminLoginDto adminLoginDto = new AdminLoginDto();
        adminLoginDto.setAccount("admin");
        adminLoginDto.setPassword("12345");

        Result login = adminController.login(adminLoginDto);
        System.out.println(login);
    }

    @Test
    void test3() {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getAdminSecretKey())//密钥
                    .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNzAwMjc2MDE1LCJhY2NvdW50IjoiYWRtaW4ifQ.LfxPz1caei-lk2U0h4O3TJQLhEwhm935mM_iGz18Pk4")//token
                    .getBody();
        } catch (Exception e) {
            System.out.println("令牌错误");
        }
        System.out.println(claims);
        System.out.println(claims.get("id"));
        System.out.println(claims.get("account"));
    }

    @Test
    void test4() {

        Page<Admin> page = Page.of(1, 2);

         String account = "d";
         String name = "d";
         Integer status = 1;

        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<Admin>()
                .like(account != null, Admin::getAccount, account)
                        .like(name != null ,Admin::getName,name)
                                .eq(status != null,Admin::getStatus,status);

        page = adminService.page(page, wrapper);

        System.out.println(page.getPages());
        System.out.println(page.getTotal());
        System.out.println(page.getRecords());

    }

    @Test
    void test5() {



        String account = "m" ;
        String name = "m";


        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<Admin>()
                .like( account != null,Admin::getAccount, account)
                .like(name != null ,Admin::getName,name);

        List<Admin> list = adminService.list(wrapper);
        System.out.println(list);
    }


    @Test
    void test6() {

        ThreadLocal threadLocal = Thread.getThreadLocal();
        String account = (String) threadLocal.get();
        System.out.println(account);


    }

    @Test
    void test7() {

        UserPageDto userPageDto = new UserPageDto();

        userPageDto.setPageSize(2);
        userPageDto.setPageCurrent(1);

        PageVo<User> userPageVo = userService.selectPage(userPageDto);
        System.out.println(userPageVo);


    }

    @Test
    void test8() {


    }
















}
