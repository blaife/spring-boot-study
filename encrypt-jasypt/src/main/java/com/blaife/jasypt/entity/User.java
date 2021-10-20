package com.blaife.jasypt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author blaife
 * @description TODO
 * @data 2021/5/9 9:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private long id;
    private String name;
    private int age;
    private int sex;

}
