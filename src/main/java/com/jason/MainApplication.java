package com.jason;

import com.jason.server.NettyServerCreater;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * SpringBoot启动类
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/6/14 11:18
 */
@SpringBootConfiguration
@ComponentScan
public class MainApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        new NettyServerCreater().startUp();
        System.out.println("----------Springboot启动");
    }
}
