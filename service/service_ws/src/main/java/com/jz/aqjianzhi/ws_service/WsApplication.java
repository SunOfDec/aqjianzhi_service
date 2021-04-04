package com.jz.aqjianzhi.ws_service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"com.jz.aqjianzhi"})  // 扫描service_base下的组件
public class WsApplication {
    public static void main(String[] args) {
        SpringApplication.run(WsApplication.class, args);
    }
}
