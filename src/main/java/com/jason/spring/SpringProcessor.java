package com.jason.spring;

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
    private AnnotationConfigApplicationContext applicationContext;

    private SpringProcessor() {}

    public static SpringProcessor getInstance() {
        return instance;
    }

    /**
     * 初始化
     * @return: void
     * @date: 2019/4/30 19:32
     */
    public void init() {
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ApplicationConfig.class);
        System.out.println("-------------------Spring启动");
        applicationContext.refresh();
    }

    /**
     * 获得Spring管理的bean
     * @param beanName
     * @return: java.lang.Object
     * @date: 2019/5/1 11:31
     */
    public Object getBean(String beanName) {
        if (applicationContext == null) {
            // 未初始化
            init();
        }
        return applicationContext.getBean(beanName);
    }

}
