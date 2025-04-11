package com.sample.cache_impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class CacheImplApplication {
    private final static Logger LOGGER = LoggerFactory.getLogger(CacheImplApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CacheImplApplication.class, args);
        LOGGER.info("CACHE-IMPL-SERVICE STARTED SUCCESSFULLY");
    }

}
