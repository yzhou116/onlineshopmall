package com.yizhou.mymall;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yizhou.mymall.mapper")
public class MymallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MymallApplication.class, args);
    }

}
