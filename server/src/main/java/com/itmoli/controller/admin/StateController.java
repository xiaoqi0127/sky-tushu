package com.itmoli.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.itmoli.constant.ComConstant;
import com.itmoli.dto.StatePageDto;
import com.itmoli.po.State;
import com.itmoli.service.BookService;
import com.itmoli.service.StateService;
import com.itmoli.vo.PageVo;
import com.itmoli.vo.Result;
import com.itmoli.vo.StateVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admin/state")
@Slf4j
@RequiredArgsConstructor
public class StateController {

    private final BookService bookService;
    private final StateService stateService;


    /**
     * 分页查询
     *
     * @param
     * @return
     */
    @GetMapping()
    public Result selectPage(StatePageDto statePageDto) {

        log.info("查询数据：{}", statePageDto);

        PageVo<State> statePageVo = stateService.selectPage(statePageDto, ComConstant.NUMBER);


        if (statePageVo != null) {
            return Result.success(statePageVo);
        }

        return Result.err();
    }

/**
 * 已借出分页查询
 *
 * @param statePageDto 状态分页数据传输对象
 * @return 查询结果
 */
@GetMapping("/return")
public Result selectPager(StatePageDto statePageDto) {
    log.info("查询数据：{}", statePageDto);  // 记录日志，输出查询数据

    PageVo<State> statePageVo = stateService.selectPage(statePageDto, ComConstant.NUMBER_ONE);  // 调用状态服务的分页查询方法

    return Optional.ofNullable(statePageVo)
            .map(Result::success)
            .orElse(Result.err());  // 返回查询结果，使用Optional避免空指针异常
}

    /**
     * id查询
     *
     * @param
     * @return
     */
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id) {

        log.info("查询数据：{}", id);

        State state = stateService.getById(id);

        //拷贝数据
        StateVo stateVo = BeanUtil.copyProperties(state, StateVo.class);

        if (state != null) {
            return Result.success(stateVo);
        }

        return Result.err();
    }

    /**
     * 根据id批量删除状态
     *
     * @param ids 待删除状态的id列表
     * @return 删除操作结果
     */
    @DeleteMapping("/states")
    public Result deleteStatesByIds(@RequestParam List<Integer> ids) {
        try {
            log.info("删除状态数据：{}", ids);  // 使用日志记录删除状态数据的id列表
            boolean success = stateService.removeBatchByIds(ids);  // 调用stateService的removeBatchByIds方法进行批量删除操作
            if (success) {
                return Result.success("状态删除成功");  // 若删除成功，则返回成功的结果
            } else {
                return Result.err("状态删除失败");  // 若删除失败，则返回失败的结果
            }
        } catch (Exception e) {
            log.error("删除状态数据时发生异常", e);  // 捕获异常并记录异常信息
            return Result.err("删除状态数据时发生异常");  // 返回异常信息
        }
    }

    /**
     * 根据id修改状态
     *
     * @param id     要修改状态的数据id
     * @param status 要修改的状态值
     * @return 修改结果
     */
    @PutMapping("/{id}/{status}")  // 使用PUT方法映射请求，路径中包含id和status参数
    public Result updateStatus(@PathVariable Integer id, @PathVariable Integer status) {  // 定义updateStatus方法，接收id和status作为路径参数
        try {
            log.info("修改的数据：id={}, status={}", id, status);  // 记录修改的数据id和status
            int result = stateService.updateStatus(id, status);  // 调用stateService的updateStatus方法，传入id和status参数
            if (result == 1) {  // 如果修改成功
                return Result.success();  // 返回成功的结果
            } else {  // 如果修改失败
                return Result.err("Update failed");  // 返回修改失败的错误信息
            }
        } catch (Exception e) {  // 捕获异常
            log.error("Failed to update status", e);  // 记录更新状态失败的错误信息
            return Result.err("Failed to update status");  // 返回更新状态失败的错误信息
        }
    }
}
