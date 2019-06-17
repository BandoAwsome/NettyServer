package com.jason.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring管理器
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/4/30 19:27
 */
public class SpringProcessor {

    /** 单例 */
    private static final SpringProcessor instance = new SpringProcessor();

    /** Spring容器 */
    private ApplicationContext applicationContext;

    private SpringProcessor() {}

    public static SpringProcessor getInstance() {
        return instance;
    }

    /**
     * 初始化
     * @return: void
     * @date: 2019/4/30 19:32
     */
    public void init(ApplicationContext applicationContext) {
//        applicationContext = new AnnotationConfigApplicationContext();
//        applicationContext.register(ApplicationConfig.class);
//        System.out.println("-------------------Spring启动");
//        applicationContext.refresh();
        this.applicationContext = applicationContext;
    }

    /**
     * 获得Spring管理的bean
     * @param beanName
     * @return: java.lang.Object
     * @date: 2019/5/1 11:31
     */
    public Object getBean(String beanName) throws Exception {
        if (applicationContext == null) {
            // 未初始化
            throw new Exception("Spring未正确初始化");
        }
        return applicationContext.getBean(beanName);
    }

}
