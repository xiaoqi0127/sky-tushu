package com.itmoli.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatePageDto {

    private String name;
    private String book;

    //每页多少条
    private Integer pageSize;
    //当前多少页
    private Integer pageCurrent;
}
