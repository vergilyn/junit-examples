package com.vergilyn.examples.annotation.json.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

/**
 * Example {@link SpringBootApplication @SpringBootApplication} for use with
 * {@link JsonTest @JsonTest} tests.
 *
 * @author Phillip Webb
 */
@SpringBootApplication(exclude = CassandraAutoConfiguration.class)
public class ExampleJsonApplication {

}