package com.ssafy.happyhouse;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class HappyHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(HappyHouseApplication.class, args);
    }

}
