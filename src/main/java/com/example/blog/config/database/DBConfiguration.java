package com.example.blog.config.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfiguration {

    @ConfigurationProperties(prefix = "dbconfigs")
    @Bean
    public DBConfigMap dbConfigMap() {
        System.out.println("dbConfigMap");
        return new DBConfigMap();
    }
}
