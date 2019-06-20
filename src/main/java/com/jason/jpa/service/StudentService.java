package com.jason.jpa.service;

import com.jason.jpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * StudentService
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/6/19 19:24
 */
@Transactional
public interface StudentService extends JpaRepository<Student, Integer> {

    @Modifying
    @Query(nativeQuery=true, value = "update student set id = 2 where id = 1")
    void updateSome();
}
