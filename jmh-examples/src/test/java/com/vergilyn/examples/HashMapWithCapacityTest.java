package com.vergilyn.examples;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * <pre>
 * 分别定义两个基准测试的方法testHashMapWithoutSize和 testHashMap，这两个基准测试方法执行流程是：
 *  1) 每个方法执行前都进行5次预热执行，每隔1秒进行一次预热操作，
 *  2） 预热执行结束之后进行5次实际测量执行，每隔1秒进行一次实际执行，
 *  3) 我们此次基准测试测量的是平均响应时长，单位是us。
 * </pre>
 * @author VergiLyn
 * @date 2020-01-13
 * @see <a href="https://mp.weixin.qq.com/s/JkbtjPnaWNQ57t7MSb1JlQ">Java 并发测试神器：基准测试神器-JMH</a>
 * @see org.openjdk.jmh.annotations.Setup
 */
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)  // 预热5次，每次间隔1s
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)  // 测试5次，每次间隔1s
public class HashMapWithCapacityTest {
    private static final List<User> LIST;
    public static final int CAPACITY = 1000;

    static {
        LIST = Stream.iterate(new User(1), user -> new User(user.id + 1))
                .limit(CAPACITY)
                .collect(Collectors.toList());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void newHashMapWithCapacity() {
        Map<Integer, String> map = new HashMap<>(CAPACITY);
        LIST.forEach(user -> map.put(user.id, user.name));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void newHashMap() {
        Map<Integer, String> map = new HashMap<>();
        LIST.forEach(user -> map.put(user.id, user.name));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(HashMapWithCapacityTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Data
    @ToString
    @NoArgsConstructor
    private static class User{
        private int id;
        private String name;

        public User(int id) {
            this.id = id;
            this.name = String.format("name - %04d", id);
        }
    }
}
