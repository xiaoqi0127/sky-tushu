package com.itmoli.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itmoli.constant.CodeConstant;
import com.itmoli.constant.MsgConstant;
import com.itmoli.po.Admin;
import com.itmoli.query.Thread;
import com.itmoli.service.AdminService;
import com.itmoli.vo.AdminSelectByVo;
import com.itmoli.vo.PageVo;
import com.itmoli.vo.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/select")
@Slf4j
@RequiredArgsConstructor
public class SelectController {


    private final AdminService adminService;

    /**
     * 分页条件查询
     * @param
     * @return
     */
    @GetMapping()
    public Result select( String account, String name, Integer status,  Integer pageSize, Integer pageCurrent){

        log.info("查询数据：{}",account);
        log.info("查询数据：{}",name);
        log.info("查询数据：{}",status);
        log.info("查询数据：{}",pageSize);
        log.info("查询数据：{}",pageCurrent);

        PageVo<Admin> pageVo = adminService.select(account,name,status,pageSize,pageCurrent);

        return new Result(CodeConstant.SUCCESS,MsgConstant.SUCCESS,pageVo);
    }

    /**
     * 用户详情
     * @param
     * @return
     */
    @GetMapping("/byName")
    public Result selectByName(){

        log.info("根据账号查询");

        ThreadLocal threadLocal = Thread.getThreadLocal();
        String account = (String) threadLocal.get();

        if (account != null && account != " ") {
            LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<Admin>()
                    .eq(account != null,Admin::getAccount,account);

            Admin admin = adminService.getOne(wrapper);

            AdminSelectByVo adminSelectByVo = BeanUtil.copyProperties(admin, AdminSelectByVo.class);

            log.info("线程储存的account：{}",account);

            return new Result(CodeConstant.SUCCESS,MsgConstant.SUCCESS,adminSelectByVo);
        }
        return new Result(CodeConstant.ERR,MsgConstant.ERR);
    }
}
