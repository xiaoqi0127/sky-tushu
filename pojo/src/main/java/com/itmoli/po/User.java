package com.itmoli.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
        private int id;
        private String account ;
        private String name;
        private String password;
        private String phone;
        private String email;
        private String address;
}
