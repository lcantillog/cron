package com.cron.palatsi.cron.pojo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

@Component
public class PropertyLogger implements CommandLineRunner {

    private final Environment env;

    public PropertyLogger(Environment env) {
        this.env = env;
    }

    @Override
    public void run(String... args) {
        System.out.println("Propiedades cargadas:");
        System.out.println("getActiveProfiles: " + env.getActiveProfiles());
    }
}