package com.sunjh.xiyiji;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass=true,exposeProxy=true)
public class XiyijiApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiyijiApplication.class, args);
    }

}

