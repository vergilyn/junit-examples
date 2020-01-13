package com.vergilyn.examples.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author VergiLyn
 * @blog http://www.cnblogs.com/VergiLyn/
 * @date 2018/4/16
 */
@SpringBootApplication
public class JavaTestApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(JavaTestApplication.class);
        // application.setAdditionalProfiles("db");
        application.run(args);
    }
}
