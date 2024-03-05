package com.itmoli.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.itmoli.constant.CodeConstant;
import com.itmoli.constant.ComConstant;
import com.itmoli.constant.MsgConstant;
import com.itmoli.dto.AdminDto;
import com.itmoli.dto.AdminLoginDto;
import com.itmoli.dto.AdminPasswordDto;
import com.itmoli.po.Admin;
import com.itmoli.query.AdminPo;
import com.itmoli.query.Thread;
import com.itmoli.utils.JwtCreate;
import com.itmoli.vo.AdminSelectByVo;
import com.itmoli.vo.AdminVo;
import com.itmoli.vo.Result;
import com.itmoli.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/admin/")
@Slf4j
@RequiredArgsConstructor
public class AdminController {


    private final AdminService adminService;

    private final JwtCreate jwtCreate;


    /**
     * 登录
     * @param adminLoginDto 登陆的信息
     * @return
     */

    @PostMapping("login")
    public Result login(@RequestBody AdminLoginDto adminLoginDto){

        Admin admin = adminService.login(adminLoginDto);

        if (admin != null){
            log.info("有用户");

            String jwt = jwtCreate.getJwt(admin);

            AdminVo adminVo = new AdminVo();
            adminVo.setAdminName(admin.getAccount());
            adminVo.setToken(jwt);

            log.info("jwt令牌是：{}" ,jwt);
            return new Result(CodeConstant.SUCCESS, MsgConstant.LOGIN_SUCCESS,adminVo);
        }
        return new Result(CodeConstant.ERR,MsgConstant.LOGIN_FINAL,null);
    }

    /**
     * 返回用户名
     * @param
     * @return
     */
    @GetMapping("selectName")
    public Result selectName(){

        log.info("根据账号查询");

        ThreadLocal threadLocal = Thread.getThreadLocal();
        String account = (String) threadLocal.get();

        log.info("线程储存的account：{}",account);

        return new Result(CodeConstant.SUCCESS,MsgConstant.SUCCESS,account);
    }


    /**
     * 新增管理员
     */
    @PostMapping("save")
    public Result save(@RequestBody AdminDto adminDto){

        log.info("管理员新增数据：{}",adminDto);

        int add = adminService.add(adminDto);

        if (add == 1){
            log.info("管理员添加成功");
            return new Result(CodeConstant.SUCCESS,MsgConstant.SUCCESS,null);
        }

        return new Result(CodeConstant.ERR,MsgConstant.ERR,null);
    }

    /**
     * 删除操作
     * @return
     */
    @DeleteMapping ("delete")
    public Result delete(@RequestParam List<Integer> ids){

        log.info("需要删除的ids【】 ：{}",ids);

        //删除操作
        int i = adminService.delete(ids);

        if (i > 0){
            //返回结果
            return new Result(CodeConstant.SUCCESS,MsgConstant.SUCCESS,null);
        }

        return new Result(CodeConstant.ERR,MsgConstant.ERR,null);
    }

    /**
     * 状态切换
     * @return
     */
    @PutMapping ("{status}/{id}")
    public Result updateStatus(@PathVariable int status,@PathVariable int id){

        log.info("需要修改为status ：{}",status);
        log.info("需要修改的id ：{}",id);

        int updated = adminService.updateStatus(id, status);

        if (updated > 0){
            return new Result(CodeConstant.SUCCESS,MsgConstant.SUCCESS);
        }


        return new Result(CodeConstant.ERR,MsgConstant.ERR);
    }

    /**
     * 根据id查询
     * @return
     */
    @GetMapping ("{id}")
    public Result selectById(@PathVariable int id){
        log.info("需要查询的id ：{}",id);

        Admin admin = adminService.getById(id);

        if ( admin != null){
            AdminSelectByVo adminSelectByVo = BeanUtil.copyProperties(admin, AdminSelectByVo.class);
            return new Result(CodeConstant.SUCCESS,MsgConstant.SUCCESS,adminSelectByVo);
        }
        return new Result(CodeConstant.ERR,MsgConstant.ERR);
    }

    /**
     * 更新数据
     * @return
     */
    @PutMapping ("update")
    public Result update(@RequestBody AdminDto adminDto){

        log.info("update修改数据：{}",adminDto);

        AdminPo adminPo = BeanUtil.copyProperties(adminDto, AdminPo.class);

        int i = adminService.adminUpdate(adminPo);

        if (i != 0){
            return new Result(CodeConstant.SUCCESS,MsgConstant.SUCCESS);
        }

        return new Result(CodeConstant.ERR,MsgConstant.ERR);
    }

    /**
     * 修改密码
     * @return
     */
    @PutMapping ("updatePass")
    public Result updatePass(@RequestBody AdminPasswordDto passwordDto){

        log.info("修改密码数据：{}",passwordDto);

        int i = adminService.updatePass(passwordDto);

        if (i == ComConstant.NUMBER_ONE){
            return new Result(CodeConstant.SUCCESS,MsgConstant.SUCCESS);
        }
        if (i == ComConstant.NUMBER_TWO){
            return new Result(CodeConstant.UNKNOWN,MsgConstant.PASSWORD_ERR);
        }

        return new Result(CodeConstant.ERR,MsgConstant.ERR);
    }
}





















