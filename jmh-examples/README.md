# jmh-examples

JMH(Java Micro-benchmark Harness):
+ [OpenJDK JMH](http://openjdk.java.net/projects/code-tools/jmh/)
+ [Openjdk Jmh Samples](http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/)

专门用于代码微基准测试的工具套件。何谓Micro Benchmark呢？  
单的来说就是基于方法层面的基准测试，精度可以达到 μs（微秒级）。
当你定位到热点方法，希望进一步优化方法性能的时候，就可以使用JMH对优化的结果进行量化的分析。

和其他竞品相比——如果有的话，JMH最有特色的地方就是，它是由Oracle内部实现JIT的那拨人开发的。


**JMH比较典型的应用场景有：**
1) 想准确的知道某个方法需要执行多长时间，以及执行时间和输入之间的相关性；
2) 对比接口不同实现在给定条件下的吞吐量；
3) 查看多少百分比的请求在多长时间内完成；

