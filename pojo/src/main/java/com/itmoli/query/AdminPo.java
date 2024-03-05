package com.itmoli.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前端传过来的数据进行封装，用于程序间使用
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminPo {
    private String account ;
    private String name;
    private String gender;
    private String phone;
    private String address;
    private String email;
}
