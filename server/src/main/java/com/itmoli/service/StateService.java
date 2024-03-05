package com.itmoli.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmoli.dto.StatePageDto;
import com.itmoli.po.State;
import com.itmoli.vo.PageVo;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface StateService extends IService<State> {

    /**
     * 分页查询
     * @return
     */
    PageVo<State> selectPage(StatePageDto statePageDto,Integer status);

    /**
     * 修改状态
     *
     */
    int updateStatus(Integer id,Integer status);
}
