package com.jason.spring;

import org.springframework.context.annotation.*;

/**
 * Spring配置类
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/4/30 19:34
 */
@Configuration
@ComponentScan({"com.jason"})
@PropertySources({
        @PropertySource("classpath:config.properties"),
})
public class ApplicationConfig {

}
