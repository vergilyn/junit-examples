package com.vergilyn.examples;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author vergilyn
 * @date 2020-05-07
 */
@BenchmarkMode(Mode.All) // 测试方法平均执行时间
@OutputTimeUnit(TimeUnit.MILLISECONDS) // 输出结果的时间粒度为微秒
@State(Scope.Thread)
public class JmhEstimateTest {
    private static final AtomicInteger INTEGER = new AtomicInteger(0);

    private static final Options OPTIONS = new OptionsBuilder()
            .include(JmhEstimateTest.class.getSimpleName())
            .forks(1)
            .threads(1)
            .warmupIterations(2)
            .warmupBatchSize(1000)
            .measurementIterations(10)
            .measurementBatchSize(2000)
            .mode(Mode.SingleShotTime)  // 优先级高于 @BenchmarkMode
            .timeUnit(TimeUnit.MICROSECONDS)  // // 优先级高于 @OutputTimeUnit
            // .timeout()
            .build();

    public static void main(String[] args) throws RunnerException {
        new Runner(OPTIONS).run();
    }

    @TearDown
    public void tearDown(){
        int expected = OPTIONS.getWarmupIterations().get() * OPTIONS.getWarmupBatchSize().get()
                + OPTIONS.getMeasurementIterations().get() * OPTIONS.getMeasurementBatchSize().get();

        // junit4
        // org.junit.Assert.assertEquals(expected, INTEGER.get());
    }

    @Benchmark
    public void incr(){
        INTEGER.incrementAndGet();
    }

    public static void print(String mark){
        System.out.println(mark + " >>>> " + INTEGER.get());
    }
}
