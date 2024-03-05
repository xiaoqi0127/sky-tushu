package com.itmoli.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmoli.constant.ComConstant;
import com.itmoli.dto.AdminDto;
import com.itmoli.dto.AdminLoginDto;
import com.itmoli.dto.AdminPasswordDto;
import com.itmoli.po.Admin;
import com.itmoli.mapper.AdminMapper;
import com.itmoli.query.AdminPo;
import com.itmoli.query.ThreadOne;
import com.itmoli.service.AdminService;
import com.itmoli.vo.PageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements AdminService {


    private final AdminMapper adminMapper;

    /**
     * 登录·
     * @param adminLoginDto 前端传过来的数据封装
     */
    @Override
    public Admin login(AdminLoginDto adminLoginDto) {

        return adminMapper.login(adminLoginDto);

    }

    /**
     * 查询
     * @param
     * @return
     */

    @Override
    public PageVo<Admin> select(String account,String name, Integer status,Integer pageSize,Integer pageCurrent) {

        Page<Admin> page = Page.of(pageCurrent,pageSize);

        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<Admin>()
                .like(account != null,Admin::getAccount,account)
                .like(name != null,Admin::getName,name)
                .eq(status != null,Admin::getStatus,status);

        page = adminMapper.selectPage(page, wrapper);

        PageVo<Admin> pageVo = new PageVo<>();
        pageVo.setTotal((int) page.getTotal());
        pageVo.setPages((int) page.getPages());
        pageVo.setList(page.getRecords());

        return pageVo;
    }


    /**
     * 新增管理员
     * @param adminDto 管理员数据
     */
    @Override
    public int add(AdminDto adminDto) {

        Admin admin = BeanUtil.copyProperties(adminDto, Admin.class);

        admin.setCreateDate(LocalDateTime.now());
        admin.setUpdateDate(LocalDateTime.now());
        admin.setStatus(ComConstant.NUMBER_ONE);

        return adminMapper.insert(admin);
    }

    /**
     * 删除数据
     * @param ids 根据id删除数据
     */
    @Override
    public int delete(List ids) {
        //调用删除方法，批量删除
        return adminMapper.deleteBatchIds(ids);
    }


    /**
     * 修改状态
     * @param id id
     * @param status 状态
     */
    @Override
    public int updateStatus(int id, int status) {

        int i = adminMapper.updateStatus(id, status);
        return i;
    }

    /**
     * 修改数据
     * @param adminPo
     * @return
     */
    @Override
    public int adminUpdate(AdminPo adminPo) {

        ThreadLocal threadLocal = ThreadOne.getThreadLocal();
        Integer id = (Integer) threadLocal.get();

        Admin admin1 = adminMapper.selectById(id);

        Admin admin = BeanUtil.copyProperties(adminPo, Admin.class);
        admin.setUpdateDate(LocalDateTime.now());
        admin.setStatus(admin1.getStatus());

        LambdaUpdateWrapper<Admin> wrapper = new LambdaUpdateWrapper<Admin>()
                .eq(Admin::getId,id);

        return adminMapper.update(admin, wrapper);
    }

    @Override
    public int updatePass(AdminPasswordDto passwordDto) {

        //获取当前登录id
        Integer id = (Integer) ThreadOne.getThreadLocal().get();

        //获取当前登录id的用户
        Admin admin = adminMapper.selectById(id);

        //判断两个密码是否相等
        if (admin.getPassword().equals(passwordDto.getOldPassword()) ){
            int i = adminMapper.updatePass(id, passwordDto.getPassword());
            return i;
        }

        return ComConstant.NUMBER_TWO;
    }


}
