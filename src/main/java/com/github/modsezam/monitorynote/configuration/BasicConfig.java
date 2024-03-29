package com.github.modsezam.monitorynote.configuration;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Configuration
public class BasicConfig {

    @Bean(name = "myName")
    public String myNameBean() {
        String randomString = UUID.randomUUID().toString();

        return "Marcin - " + randomString.substring(0, 5);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "ftpClient")
    public FTPClient ftpClient() {
        return new FTPClient();
    }
}
