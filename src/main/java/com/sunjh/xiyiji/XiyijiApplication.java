package com.sunjh.xiyiji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true,exposeProxy=true)
public class XiyijiApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiyijiApplication.class, args);
    }

}
