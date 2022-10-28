package com.cantaur.practice.config.security;

import com.cantaur.practice.config.resources.JwtToken;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.jwt.token")
public class JwtTokenConfig {
    private String secretKey;

    private int expireLength;

    @Bean(name = "jwtToken")
    public JwtToken getJwtToken() {
        return new JwtToken(this.secretKey, this.expireLength);
    }
}
