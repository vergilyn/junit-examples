package com.vergilyn.examples;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author VergiLyn
 * @date 2020-01-13
 * @see <a href="https://blog.csdn.net/lxbjkben/article/details/79410740">JMH使用说明</a>
 */
@BenchmarkMode(Mode.AverageTime) // 测试方法平均执行时间
@OutputTimeUnit(TimeUnit.MICROSECONDS) // 输出结果的时间粒度为微秒
@State(Scope.Thread) // 每个测试线程一个实例
@Slf4j
public class JmhSampleTest {

    @Benchmark
    public String stringConcat() {
        String a = "a";
        String b = "b";
        String c = "c";
        String s = a + b + c;
        // log.debug(s);
        return s;
    }

    public static void main(String[] args) throws RunnerException {
        // 使用一个单独进程执行测试，执行5遍warm-up，然后执行5遍测试
        Options opt = new OptionsBuilder()
                .include(JmhSampleTest.class.getSimpleName())
                .forks(1)   // 执行线程数
                .warmupIterations(5)    // 预热次数
                .measurementIterations(5)   // 测试次数
                .build();
        new Runner(opt).run();
    }
}
