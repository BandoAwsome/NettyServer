package com.jason.action;

import com.jason.jpa.entity.Student;
import com.jason.jpa.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

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
        // 数据库里插入50w条数据
        Random random = new Random();
        for (int i = 0; i < 1000000; i++) {
            Student student = new Student();
            // 名字随机
            String name = "";
            // 名字长度随机
            int nameLength = random.nextInt(6) + 4;
            for (int j = 0; j < nameLength; j++) {
                name += (char)('a' + random.nextInt(25));
            }
            student.setName(name);
            // 分数随机
            student.setScore(random.nextInt(100));
            // 年级随机
            student.setGrade(random.nextInt(5) + 1);
            studentService.save(student);
        }
        return "" + studentService.findAll().size();
    }
}
