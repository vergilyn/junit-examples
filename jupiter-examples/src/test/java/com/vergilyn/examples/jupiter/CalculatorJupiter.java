package com.vergilyn.examples.jupiter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @see <a href="https://github.com/junit-team/junit5-samples/blob/r5.7.0/junit5-jupiter-starter-maven/src/test/java/com/example/project/CalculatorTests.java">CalculatorTests.java</a>
 */
public class CalculatorJupiter {

    @BeforeAll
    static void beforeAll(){
        System.out.println("beforeAll()");
    }

    @BeforeEach
    void beforeEach(){
        System.out.println("beforeEach()");
    }

    @Test
    @DisplayName("1 + 1 = 2")
    void addsTwoNumbers() {
        System.out.println("addsTwoNumbers()");

        Calculator calculator = new Calculator();
        assertEquals(3, calculator.add(1, 1), "1 + 1 should equal 2");
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource({
            "0,    1,   1",
            "1,    2,   3",
            "49,  51, 100",
            "1,  100, 101"
    })
    void add(int first, int second, int expectedResult) {
        Calculator calculator = new Calculator();
        assertEquals(expectedResult, calculator.add(first, second),
                () -> first + " + " + second + " should equal " + expectedResult);
    }

    public static class Calculator {

        public int add(int a, int b) {
            return a + b;
        }

    }
}