package com.himmerland.hero.config;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Component
public class HeroConfigProperties {

    private final Properties props = new Properties();

    public HeroConfigProperties() {
        try (FileInputStream in = new FileInputStream("config.properties")) {
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Kunne ikke læse config.properties i projekt-roden", e);
        }
    }

    public String getHost() {
        return props.getProperty("host");
    }

    public int getPort() {
        String value = props.getProperty("port", "587");
        return Integer.parseInt(value);
    }

    public String getUsername() {
        return props.getProperty("username");
    }

    public String getPassword() {
        return props.getProperty("password");
    }

    public String getRecipient() {
        return props.getProperty("recipient");
    }

    public String getFrom() {
        return props.getProperty("from");
    }

    public boolean isAutosend() {
        return Boolean.parseBoolean(props.getProperty("autosend", "false"));
    }
}
