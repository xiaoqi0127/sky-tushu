package com.itmoli.controller.admin;

import com.itmoli.dto.UserPageDto;
import com.itmoli.po.User;
import com.itmoli.service.UserService;
import com.itmoli.vo.PageVo;
import com.itmoli.vo.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 分页查询
     * @return
     */
    @GetMapping()
        public Result selectPage(UserPageDto userPageDto){

        log.info("查询数据：{}",userPageDto);

        //调用业务层接口
        PageVo<User> userPageVo = userService.selectPage(userPageDto);

        log.info("查询出来的数据：{}",userPageVo);

        if (userPageVo != null){
            return Result.success(userPageVo);
        }

        return Result.err();
    }

    /**
     * 删除
     * @return
     */
    @DeleteMapping()
    public Result delete(@RequestParam List<Integer> ids){

        log.info("需要删除的数据id：{}",ids);

        boolean removed = userService.removeBatchByIds(ids);

        if (removed){
            return Result.success();
        }

        return Result.err();
    }
}
