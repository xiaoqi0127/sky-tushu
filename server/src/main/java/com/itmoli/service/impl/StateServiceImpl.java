package com.itmoli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmoli.dto.StatePageDto;
import com.itmoli.po.State;
import com.itmoli.mapper.StateMapper;
import com.itmoli.service.StateService;
import com.itmoli.vo.PageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class StateServiceImpl extends ServiceImpl<StateMapper, State> implements StateService {

    private final StateMapper stateMapper;
    /**
     * 分页查询
     * @return
     */
    @Override
    public PageVo<State> selectPage(StatePageDto statePageDto, Integer status) {

        //创建条件查询
        LambdaQueryWrapper<State> wrapper = new LambdaQueryWrapper<State>()
                .like(statePageDto.getName() != null && statePageDto.getName() != "", State::getName, statePageDto.getName())
                .like(statePageDto.getBook() != null && statePageDto.getBook() != "",State::getBook,statePageDto.getBook())
                .eq(State::getStatus, status);

        //创建分页查询
        Page<State> page = Page.of(statePageDto.getPageCurrent(),statePageDto.getPageSize());

        //查询
        page = stateMapper.selectPage(page, wrapper);

        //封装数据
        PageVo<State> pageVo = new PageVo<>();
        pageVo.setTotal((int) page.getTotal());
        pageVo.setPages((int) page.getPages());
        pageVo.setList(page.getRecords());

        return pageVo;
    }

    /**
     * 修改状态
     * @param id
     * @param status
     * @return
     */
    @Override
    public int updateStatus(Integer id, Integer status) {

        State state = new State();
        state.setId(id);
        state.setStatus(status);
        state.setNowDate(LocalDateTime.now());

        int i = stateMapper.updateById(state);

        return i;
    }
}
