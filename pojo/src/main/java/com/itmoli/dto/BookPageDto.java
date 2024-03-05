package com.itmoli.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookPageDto {

    private String name;
    private String writer;
    private Integer status;
    private Integer clsId;

    //每页多少条
    private Integer pageSize = 5;
    //当前多少页
    private Integer pageCurrent = 1;
}
