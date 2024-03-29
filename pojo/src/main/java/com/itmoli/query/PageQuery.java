package com.itmoli.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQuery {

    private String account;
    private String name;
    private Integer status;

    //每页多少条
    private Integer pageSize;
    //当前多少页
    private Integer pageCurrent;
}
