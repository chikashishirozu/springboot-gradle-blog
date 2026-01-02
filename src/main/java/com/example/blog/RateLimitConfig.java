package com.example.blog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;
import com.google.common.util.concurrent.RateLimiter;

@Configuration
public class RateLimitConfig {
    @Bean
    public RateLimiter rateLimiter() {
        return RateLimiter.create(10.0); // 10リクエスト/秒
    }
}
