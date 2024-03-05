package com.itmoli.service;

import com.itmoli.dto.UserPageDto;
import com.itmoli.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmoli.vo.PageVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Users and global privileges 服务类
 * </p>
 *
 * @author itmoli
 * @since 2023-11-22
 */
@Transactional
public interface UserService extends IService<User> {


    /**
     * 分页查询
     * @return
     */
    PageVo<User> selectPage(UserPageDto userPageDto);

}
