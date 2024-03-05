package com.itmoli.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private int id;
    private int clsId;
    private String name;
    private String cover;
    private String writer;
    private String press;
    private String intro;

}
