package com.jason.action;

import com.jason.jpa.entity.Student;
import com.jason.jpa.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 打印Action
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/7 16:10
 */
@Component("print")
public class PrintAction extends ActionBase {

    @Autowired
    private StudentService studentService;

    public String print() {
        LocalDateTime now = LocalDateTime.now();
        List<Student> all = studentService.findAll();

        return now.toString();
    }
}
