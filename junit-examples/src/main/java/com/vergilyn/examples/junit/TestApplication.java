package com.vergilyn.examples.junit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author VergiLyn
 * @blog http://www.cnblogs.com/VergiLyn/
 * @date 2018/4/16
 */
@SpringBootApplication
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TestApplication.class);
        // application.setAdditionalProfiles("db");
        application.run(args);
    }
}
