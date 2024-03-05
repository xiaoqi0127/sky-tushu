package com.itmoli.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassBookPo {
    private String clsName;
    private String intro;
    private LocalDateTime createDate;
}
