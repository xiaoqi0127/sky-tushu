package com.itmoli.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 封装查询出的分类
 */
public class ClsVo {
    private String clsName;
    private int id;
}
