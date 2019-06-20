package com.jason.action;

import com.jason.jpa.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 测试用Action, 之后用作心跳包
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/7 16:10
 */
@Component("heartBeatAction")
public class HeartBeatAction extends ActionBase {

    @Autowired
    private StudentService studentService;

    /**
     * 测试用方法
     * @return: java.lang.String
     * @date: 2019/6/20 19:40
     */
    public String test() {
        studentService.updateSome();
        return studentService.findAll().toString();
    }
}
