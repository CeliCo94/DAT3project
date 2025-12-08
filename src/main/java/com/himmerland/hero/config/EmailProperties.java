package com.himmerland.hero.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hero.email")
public record EmailProperties(
        String from,
        String recipient,
        boolean autosend
) {}
