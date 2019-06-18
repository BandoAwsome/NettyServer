package com.jason.action;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

/**
 * Action基类
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/7 16:09
 */
@Slf4j
public class ActionBase {

    /**
     * 反射回调方法
     * @return: void
     * @date: 2019/5/7 16:11
     */
    public String dealMessage(String method) {
        try {
            return (String) this.getClass().getDeclaredMethod(method).invoke(this, null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            log.info("访问接口:" + this.getClass() + "@" + method);
        }
        return "";
    }
}
