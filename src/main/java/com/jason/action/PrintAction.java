package com.jason.action;

import org.springframework.stereotype.Component;

/**
 * 打印Action
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/7 16:10
 */
@Component("print")
public class PrintAction extends ActionBase {

    public String print() {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return "原来的服务器逻辑";
    }
}
