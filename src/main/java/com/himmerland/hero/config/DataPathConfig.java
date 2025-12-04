package com.himmerland.hero.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class DataPathConfig {

    @Bean
    public Path dataPath() {
        return Path.of("data");
    }
    
}
