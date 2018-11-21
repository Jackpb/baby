package com.babyangel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.babyangel.modules.*.dao"})
public class BabyAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BabyAdminApplication.class, args);
    }
}
