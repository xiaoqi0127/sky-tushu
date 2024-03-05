package com.itmoli.mapper;

import com.itmoli.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Users and global privileges Mapper 接口
 * </p>
 *
 * @author itmoli
 * @since 2023-11-22
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
