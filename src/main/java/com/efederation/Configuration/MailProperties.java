package com.efederation.Configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.mail")
@Data
public class MailProperties {
    private String host;
    private int port;
    private String username;
    private String password;
}
