package com.jason.action;

import org.springframework.stereotype.Component;

/**
 * 打印Action
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/7 16:10
 */
@Component("print")
public class PrintAction extends ActionBase{

    public String print() {
        return "原来的服务器逻辑";
    }
}
