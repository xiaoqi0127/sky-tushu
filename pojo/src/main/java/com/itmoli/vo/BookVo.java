package com.itmoli.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookVo {
    private int id;
    private String clsName;
    private String name;
    private String cover;
    private String writer;
    private String press;
    private String intro;
    private int status ;
    private int amount ;
}
