package com.itmoli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmoli.po.ClassBook;
import com.itmoli.query.ClassBookPo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ClassBookMapper extends BaseMapper<ClassBook> {

    /**
     * 新增
     * @param classBookPo
     * @return
     */
    @Insert("insert into class_book (cls_name,intro,create_date) value (#{clsName},#{intro},#{createDate})")
    int add(ClassBookPo classBookPo);

    /**
     * 修改数量
     */
    @Update("update class_book set  number = number+1 where id = #{id}")
    int updateNumber(Integer id);

    /**
     * 修改数量
     */
    @Update("update class_book set  number = number-1 where id = #{id}")
    int updateNumbers(Integer id);
}
