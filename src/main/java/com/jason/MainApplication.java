package com.jason;

import com.jason.spring.SpringProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * SpringBoot启动类
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/6/14 11:18
 */
@SpringBootConfiguration
@ComponentScan
@Slf4j
@EnableJpaRepositories("com.jason.jpa.service")
@EntityScan("com.jason.jpa.entity")
public class MainApplication implements CommandLineRunner {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(MainApplication.class, args);
        // 初始化Spring工厂
        SpringProcessor.getInstance().init(applicationContext);
    }

    @Override
    public void run(String... args) {
        log.info("SpringBoot启动");
    }

}
