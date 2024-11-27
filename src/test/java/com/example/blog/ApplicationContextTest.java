package com.example.blog;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ApplicationContextTest {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextTest.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        assertTrue(beanNames.length > 0, "No beans found in the application context");

        for (String beanName : beanNames) {
            logger.debug("Bean found: {}", beanName);
        }

        // 重要なBeanが正しくロードされているか確認
        assertNotNull(applicationContext.getBean("postRepository"), "PostRepository bean should exist");
    }
}

