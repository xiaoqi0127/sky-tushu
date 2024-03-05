package com.itmoli.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmoli.dto.ClassBookDto;
import com.itmoli.dto.ClassBookSaveDto;
import com.itmoli.po.ClassBook;
import com.itmoli.vo.PageVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ClassBookService extends IService<ClassBook> {

    /**
     * 分页查询
     * @return
     */
    PageVo<ClassBook> selectPage(ClassBookDto classBookDto);

    /**
     * 新增
     * @param
     * @return
     */
    int save(ClassBookSaveDto classBookSaveDto);

    /**
     *
     * @param ids
     * @return
     */
    int updateNumber(List<Integer> ids);
}
