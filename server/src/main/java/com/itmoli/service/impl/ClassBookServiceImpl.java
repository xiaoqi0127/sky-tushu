package com.itmoli.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmoli.dto.ClassBookDto;
import com.itmoli.dto.ClassBookSaveDto;
import com.itmoli.po.Book;
import com.itmoli.po.ClassBook;
import com.itmoli.mapper.BookMapper;
import com.itmoli.mapper.ClassBookMapper;
import com.itmoli.query.ClassBookPo;
import com.itmoli.service.ClassBookService;
import com.itmoli.vo.PageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassBookServiceImpl extends ServiceImpl<ClassBookMapper, ClassBook> implements ClassBookService {

    private final ClassBookMapper classBookMapper;
    private final BookMapper bookMapper;
    /**
     * 分页查询
     * @param classBookDto
     * @return
     */
    @Override
    public PageVo<ClassBook> selectPage(ClassBookDto classBookDto) {

        //创建分页查询条件
        Page<ClassBook> page = Page.of(classBookDto.getPageCurrent(),classBookDto.getPageSize());

        //创建查询条件
        LambdaQueryWrapper<ClassBook> wrapper = new LambdaQueryWrapper<ClassBook>()
                .like(classBookDto.getClsName() != null,ClassBook::getClsName,classBookDto.getClsName());
        //查询的数据
        page = classBookMapper.selectPage(page, wrapper);

        //封装数据
        PageVo<ClassBook> pageVo = new PageVo<>();
        pageVo.setTotal((int) page.getTotal());
        pageVo.setPages((int) page.getPages());
        pageVo.setList(page.getRecords());


        return pageVo;
    }


    /**
     * 新增
     * @param classBookSaveDto
     */
    @Override
    public int save(ClassBookSaveDto classBookSaveDto) {
        //拷贝数据
        ClassBookPo classBookPo = BeanUtil.copyProperties(classBookSaveDto, ClassBookPo.class);

        //填充该数据
        classBookPo.setCreateDate(LocalDateTime.now());

        //添加数据
        int i = classBookMapper.add(classBookPo);
        return i;
    }

    /**
     * 修改分类中的数量
     * @param
     */
    @Override
    public int updateNumber(List<Integer> ids) {
        int i = 0;

        log.info("ids:{}",ids);

        for (Integer id : ids) {
            Book book = bookMapper.selectById(id);

            if (book != null && book.getClsId() > 0) {
                log.info("需要删除的book：{}",book);
                classBookMapper.updateNumbers(book.getClsId());
                i = bookMapper.deleteById(id);

            }

        }


        return i;
    }
}
