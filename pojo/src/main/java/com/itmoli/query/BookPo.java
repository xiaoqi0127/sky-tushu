package com.itmoli.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookPo {

    private int id;
    private int clsId;
    private String name;
    private String cover;
    private String writer;
    private String press;
    private String intro;
    private int status ;
    private int amount ;
}
