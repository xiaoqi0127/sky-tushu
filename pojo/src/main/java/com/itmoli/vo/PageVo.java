package com.itmoli.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageVo<T> {
    //总条数
    private Integer total;
    //一共多少页
    private Integer pages;
    //当前页数据
    private List<T> list;

}
