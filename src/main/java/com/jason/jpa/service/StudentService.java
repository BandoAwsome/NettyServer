package com.jason.jpa.service;

import com.jason.jpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * StudentService
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/6/19 19:24
 */
public interface StudentService extends JpaRepository<Student, Integer> {

}
