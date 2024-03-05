package com.itmoli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmoli.dto.UserPageDto;
import com.itmoli.po.User;
import com.itmoli.mapper.UserMapper;
import com.itmoli.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmoli.vo.PageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Users and global privileges 服务实现类
 * </p>
 *
 * @author itmoli
 * @since 2023-11-22
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    private final UserMapper userMapper;

    /**
     * 分页查询
     * @param userPageDto
     * @return
     */
    @Override
    public PageVo<User> selectPage(UserPageDto userPageDto) {

        //分页条件
        Page<User> page = Page.of(userPageDto.getPageCurrent(), userPageDto.getPageSize());

        //创建查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .like(userPageDto.getAccount() != null,User::getAccount,userPageDto.getAccount())

                .like(userPageDto.getName() != null,User::getName,userPageDto.getName());

        //查询
        page = userMapper.selectPage(page,wrapper);

        PageVo<User> pageVo = new PageVo<>();
        pageVo.setTotal((int) page.getTotal());
        pageVo.setPages((int) page.getPages());
        pageVo.setList(page.getRecords());

        return pageVo;
    }
}
