package com.gyy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gyy.modules.sys.mapper")
public class YyAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(YyAdminApplication.class, args);
    }

}
