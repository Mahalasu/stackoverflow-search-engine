package com.gsdai.searchengine;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gsdai.searchengine.dao")
public class SearchEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchEngineApplication.class, args);
    }

}


