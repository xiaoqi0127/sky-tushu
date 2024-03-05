package com.itmoli.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmoli.dto.BookDto;
import com.itmoli.dto.BookPageDto;
import com.itmoli.dto.StateDto;
import com.itmoli.po.Book;
import com.itmoli.vo.BookVo;
import com.itmoli.vo.PageVo;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookService extends IService<Book> {

    /**
     * 分页查询
     * @return
     */
    PageVo<BookVo> selectPage(BookPageDto bookPage);

    /**
     * 修改操作
     *
     */
    int bookUpdate(BookDto bookDto);

    /**
     * 借阅书籍
     *
     */
    boolean borrow(StateDto stateDto);

    /**
     * 修改数量
     * @param
     * @return
     */
    int updateNumber(Integer id);
}
