package com.itmoli.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.itmoli.dto.BookDto;
import com.itmoli.dto.BookPageDto;
import com.itmoli.dto.StateDto;
import com.itmoli.po.Book;
import com.itmoli.po.ClassBook;
import com.itmoli.service.BookService;
import com.itmoli.service.ClassBookService;
import com.itmoli.utils.AliOSSUtils;
import com.itmoli.vo.BookVo;
import com.itmoli.vo.ClsVo;
import com.itmoli.vo.PageVo;
import com.itmoli.vo.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("admin/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final ClassBookService classBookService;


    /**
     * 查询分类名称
     * @return
     */
    @GetMapping("/selectCls")
    public Result selectCls(){
        //分类集合
        List<ClassBook> list = classBookService.list();

        //封装集合
        List<ClsVo> bookList = new ArrayList<>();

        //创建集合所需的对象
        ClsVo clsVo = null;

        for (ClassBook classBook : list) {

            //封装对象
            clsVo = new ClsVo();
            clsVo.setId(classBook.getId());
            clsVo.setClsName(classBook.getClsName());

            //加入返回前端的集合
            bookList.add(clsVo);
        }

        if (bookList != null && bookList.size() > 0){
            return Result.success(bookList);
        }

        return Result.err();
    }

    /**
     * 分页查询
     * @param bookPageDto
     * @return
     */
    @GetMapping()
    public Result selectPage(BookPageDto bookPageDto){

        log.info("查询数据：{}",bookPageDto);

        PageVo<BookVo> pageVo = bookService.selectPage(bookPageDto);

        if (pageVo != null){
            return Result.success(pageVo);
        }

        return Result.err();
    }

    /**
     * 上传文件
     * @param
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        log.info("上传的文件名称：{}",file.getOriginalFilename());

        //上传文件
        String url = AliOSSUtils.upload(file);

        if (url != null){
          return Result.success(url);
        }

        return Result.err("上传错误！");
    }

    /**
     * 新增图书
     * @param
     * @return
     */
    @PostMapping()
    public Result save(@RequestBody BookDto bookDto){

        log.info("接受的数据：{}",bookDto);

        Book book = BeanUtil.copyProperties(bookDto, Book.class);

        log.info("拷贝之后的数据：{}",book);

        boolean flag = bookService.save(book);

        int i = bookService.updateNumber(book.getClsId());

        return flag && i == 1 ? Result.success() : Result.err();
    }

    /**
     * 根据id查询
     * @param
     * @return
     */
    @GetMapping("/{id}")
    public Result selectById(@PathVariable int id){
        //查询书籍
        Book book = bookService.getById(id);
        //查询书籍分类
        ClassBook classBook = classBookService.getById(book.getClsId());
        String name = null;
        if (classBook != null){
            name = classBook.getClsName();
        }
        //拷贝书籍
        BookVo bookVo = BeanUtil.copyProperties(book, BookVo.class);
        bookVo.setClsName(name);

        if (book != null){
            return Result.success(bookVo);
        }

        return Result.err();
    }

    /**
     * 删除
     * @param
     * @return
     */
    @DeleteMapping()
    public Result delete(@RequestParam List<Integer> ids){
        //TODO 可以添加一个删除书籍是查询一下借阅表，看有没有借阅出去
        //根据id删除
        int i = classBookService.updateNumber(ids);
        if (i > 0){
            return Result.success();
        }

        return Result.err();
    }

    /**
     * 修改
     * @param
     * @return
     */
    @PutMapping()
    public Result update(@RequestBody BookDto bookDto ) {

        log.info("修改的数据：{}",bookDto);

        //调用业务层
        int i = bookService.bookUpdate(bookDto);

        if (i==1){
            return Result.success();
        }
        return Result.err();
    }

    /**
     * 新增借阅书籍
     * @param
     * @return
     */
    @PostMapping("/borrow")
    public Result borrow( @RequestBody StateDto stateDto){

        log.info("借阅书籍信息：{}",stateDto);

        boolean flag = bookService.borrow(stateDto);

        return flag ? Result.success():Result.err();
    }

}
