package com.jason.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Student表
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/6/19 19:27
 */
@Entity
@Table(name = "student")
@Data
public class Student {

    @Id
    private int id;

}
