package com.itmoli.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmoli.dto.BookDto;
import com.itmoli.dto.BookPageDto;
import com.itmoli.dto.StateDto;
import com.itmoli.po.Book;
import com.itmoli.po.ClassBook;
import com.itmoli.po.State;
import com.itmoli.mapper.BookMapper;
import com.itmoli.mapper.ClassBookMapper;
import com.itmoli.service.BookService;
import com.itmoli.service.StateService;
import com.itmoli.vo.BookVo;
import com.itmoli.vo.PageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    private final BookMapper bookMapper;
    private final ClassBookMapper classBookMapper;
    private final StateService stateService;


    /**
     * 分页查询
     * @param bookPage
     * @return
     */
    @Override
    public PageVo<BookVo> selectPage(BookPageDto bookPage) {

        //创建查询条件
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<Book>()
                .like(bookPage.getName() != null && bookPage.getName() != "", Book::getName, bookPage.getName())
                .like(bookPage.getWriter() != null && bookPage.getWriter() != "", Book::getWriter, bookPage.getWriter())
                .eq(bookPage.getClsId() != null, Book::getClsId, bookPage.getClsId())
                .eq(bookPage.getStatus() != null,Book::getStatus, bookPage.getStatus());
        //创建分页条件
        Page<Book> page = Page.of(bookPage.getPageCurrent(),bookPage.getPageSize());

        //查询

        page = bookMapper.selectPage(page, wrapper);

        //结果集合
        List<Book> bookList = page.getRecords();

        //拷贝集合
        List<BookVo> list = BeanUtil.copyToList(page.getRecords(), BookVo.class);

        log.info("拷贝之后的集合数据:{}",list);

        //遍历查询赋值

        for (int i = 0; i < bookList.size(); i++) {
            //获取本书的clsid
            int clsId = bookList.get(i).getClsId();
            //进行查询
            ClassBook classBook = classBookMapper.selectById(clsId);

            if (classBook != null){
                String name =classBook.getClsName();

                //赋值给拷贝之后的集合

                list.get(i).setClsName(name);
            }

        }
        //封装数据,
        PageVo<BookVo> pageVo = new PageVo<>();
        pageVo.setTotal((int) page.getTotal());
        pageVo.setPages((int) page.getPages());
        pageVo.setList(list);

        // 封装完的数据返回

        return pageVo;
    }

    /**
     * 更新数据
     * @param bookDto
     * @return
     */
    @Override
    public int bookUpdate(BookDto bookDto) {

        //拷贝成book数据
        Book book = BeanUtil.copyProperties(bookDto, Book.class);

        //查询当前书籍的status和amount防止拷贝之后修改元数据
        Book book1 = bookMapper.selectById(book.getId());
        book.setStatus(book1.getStatus());
        book.setAmount(book1.getAmount());

        if (book1.getClsId() != 0){
            classBookMapper.updateNumbers(book1.getClsId());
        }

        classBookMapper.updateNumber(book.getClsId());

        log.info("拷贝之后需要修改的数据：{}",book);

        int i = bookMapper.updateById(book);

        return i;
    }

    /**
     * 修改分类中的数量
     * @param
     * @return
     */
    @Override
    public int updateNumber(Integer id) {

        int i = classBookMapper.updateNumber(id);

        return i;
    }



    /**
     * 借阅书籍
     * @param
     * @return
     */
    @Override
    public boolean borrow(StateDto stateDto) {

        //改变书籍状态
        int i = bookMapper.updateStatus(stateDto.getBookId());

        //转换类型
        State state = BeanUtil.copyProperties(stateDto, State.class);
        state.setStartDate(LocalDateTime.now());

        log.info("添加的数据：{}",state);

        boolean flag = false;

        //添加书籍到状态表
        if(i == 1){
             flag = stateService.save(state);
        }


        return flag;
    }
}
