package com.itmoli.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itmoli.constant.MsgConstant;
import com.itmoli.dto.ClassBookDto;
import com.itmoli.dto.ClassBookSaveDto;
import com.itmoli.po.ClassBook;
import com.itmoli.service.ClassBookService;
import com.itmoli.vo.PageVo;
import com.itmoli.vo.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/classBook")
@RequiredArgsConstructor
@Slf4j
public class ClassBookController {

    private final ClassBookService classBookService;

    /**
     * 分页查询
     * @param classBookDto
     * @return
     */
    @GetMapping
    public Result selectPage(ClassBookDto classBookDto){

        log.info("分类查询数据：{}",classBookDto);

        //调用查询业务层
        PageVo<ClassBook> classBookPageVo = classBookService.selectPage(classBookDto);



        if (classBookPageVo != null){
            return Result.success(classBookPageVo);
        }

        return Result.err();
    }


    /**
     * id查询
     * @param
     * @return
     */
    @GetMapping("/{id}")
    public Result select(@PathVariable int id){

        log.info("查询数据id：{}",id);

        //调用查询业务

        ClassBook classBook = classBookService.getById(id);

        if (classBook != null){
            return Result.success(classBook);
        }

        return Result.err();
    }


    /**
     * 新增
     * @param
     * @return
     */
    @PostMapping
    public Result save(@RequestBody ClassBookSaveDto classBookSaveDto){

        log.info("新增数据：{}",classBookSaveDto);
        LambdaQueryWrapper<ClassBook> wrapper = new LambdaQueryWrapper<ClassBook>()
                .eq(ClassBook::getClsName,classBookSaveDto.getClsName());

        ClassBook className = classBookService.getOne(wrapper);

        if (className != null){
            return Result.err(MsgConstant.EXIST);
        }

        //调用查询业务层
        int save = classBookService.save(classBookSaveDto);


        if (save > 0){
            return Result.success();
        }

        return Result.err();
    }

    /**
     * 删除
     * @param
     * @return
     */
    @DeleteMapping
    public Result save(@RequestParam List<Integer> ids){

        log.info("删除id数据：{}",ids);

        boolean batch = classBookService.removeBatchByIds(ids);


        if (batch){
            return Result.success();
        }

        return Result.err();
    }
}
