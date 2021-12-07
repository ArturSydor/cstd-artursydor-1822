package com.lpnu.ecoplatformserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.lpnu.ecoplatformserver.**.properties")
public class EcoPlatformServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcoPlatformServerApplication.class, args);
    }

}
