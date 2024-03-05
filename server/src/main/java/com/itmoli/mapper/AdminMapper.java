package com.itmoli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmoli.dto.AdminLoginDto;
import com.itmoli.po.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 登陆
     * @param adminLoginDto
     * @return
     */
    @Select("select * from admin where account = #{account} and password = #{password}")
    Admin login(AdminLoginDto adminLoginDto);

    /**
     * 修改状态
     */
    @Update("update admin set status = #{status} where id = #{id}")
    int updateStatus(@Param("id") int id, @Param("status") int status);

    /**
     * 修改状态
     */
    @Update("update admin set password = #{password} where id = #{id}")
    int updatePass(@Param("id") int id, @Param("password") String password);

}
