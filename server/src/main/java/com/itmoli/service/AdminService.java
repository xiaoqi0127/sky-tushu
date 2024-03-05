package com.itmoli.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmoli.dto.AdminDto;
import com.itmoli.dto.AdminLoginDto;
import com.itmoli.dto.AdminPasswordDto;
import com.itmoli.po.Admin;
import com.itmoli.query.AdminPo;
import com.itmoli.vo.PageVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AdminService extends IService<Admin> {

    /**
     * 登录
     * @param adminLoginDto
     * @return
     */
    Admin login(AdminLoginDto adminLoginDto);

    /**
     * 分页查询数据
     * @param account
     * @param name
     * @param status
     * @param pageSize
     * @param pageCurrent
     * @return
     */
    PageVo<Admin> select(String account,String name, Integer status,Integer pageSize,Integer pageCurrent);

    /**
     * 新增管理员
     * @param adminDto
     * @return
     */
    int add(AdminDto adminDto);


    /**
     * 删除数据
     */
    int delete(List ids);

    /**
     * 修改状态
     */
    int updateStatus(int id,int status);

    /**
     * 修改操作
     *
     */
    int adminUpdate(AdminPo adminPo);

    /**
     * 修改操作
     *
     */
    int updatePass(AdminPasswordDto passwordDto);




}
