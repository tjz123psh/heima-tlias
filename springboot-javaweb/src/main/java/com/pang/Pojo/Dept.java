package com.pang.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dept {
    private String id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;



}
