package com.itmoli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmoli.po.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BookMapper extends BaseMapper<Book> {
    /**
     * 修改状态
     */
    @Update("update book set status = 1, amount = amount+1 where id = #{id}")
   int updateStatus(int id);
}
