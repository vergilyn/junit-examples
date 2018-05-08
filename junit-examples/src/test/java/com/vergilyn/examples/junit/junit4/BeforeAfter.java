package com.vergilyn.examples.junit.junit4;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * 文章参考：
 * <ul>
 *     <li><a href="https://www.cnblogs.com/draenei/p/5103503.html">JUnit4 中@AfterClass @BeforeClass @after @before的区别对比</a></li>
 * </ul>
 * <pre>
 |　　　　　　　　| @BeforeClass && @AfterClass                       | @Before && @After                                                               |
 |　出现次数      | 在一个类中只可以出现一次                            | 在一个类中可以出现多次，即可以在多个方法的声明前加上这两个Annotaion标签，执行顺序不确定 |
 |　运行次数      | 在类中只运行一次                                   | 在每个测试方法之前或者之后都会运行一次                                              |
 |　执行顺序      | 父类中的@BeforeClass、@Before先于子类执行；@AfterClass、@After后于之类执行                                                             |
 |　static       | 必须是static-method                               | 只能是 public void xxx()                                                         |
 |　是否执行      | 即使@BeforeClass执行异常，@AfterClass也一定会执行   | 即使@Before执行异常，@After也一定会执行                                            |
 |　跟@Repeat配合 | 执行一次
 * </pre>
 * @author VergiLyn
 * @blog http://www.cnblogs.com/VergiLyn/
 * @date 2018/5/8
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class BeforeAfter {
    private static int before = 0;
    private static int beforeClass = 0;

    /**
     * 1. 父类中的@BeforeClass先于子类中的@BeforeClass执行。
     */
    @BeforeClass
    public static void beforeClass() {
        System.out.println("beforeClass: " + beforeClass++);
    }

    /**
     * 1. 父类中的@AfterClass后于子类中的@AfterClass执行。
     * 2. 即使@BeforeClass执行异常，也会执行@AfterClass；
     */
    @AfterClass
    public static void afterClass() {
        System.out.println("afterClass");
    }

    /**
     * 每个@Test前都会执行，（多个@Test执行顺序：不算完全无序，没仔细分析（大概跟方法名有关），详细可以分析源码{@linkplain BlockJUnit4ClassRunner#withBefores(FrameworkMethod, Object, Statement)}）
     */
    @Before
    public void before(){
        System.out.println("before: " + before++);
    }

    @After
    public void after(){
        System.out.println("after");
    }

    @Test
    public void test1(){
        System.out.println("test1");
    }

    @Test
    public void test2(){
        System.out.println("test2");
    }

}
