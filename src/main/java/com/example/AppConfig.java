package com.example;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.example")
@Component
public class AppConfig {

    public AppConfig() {
        super();
        }

    @Bean
    JedisPool jedisPool() {
        return new JedisPool(new JedisPoolConfig(), "localhost"); 
    }
}

