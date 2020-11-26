package com.vergilyn.examples.testng;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Slf4j
public class MultiThreadTestng {
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    @BeforeClass
    public void beforeClass(){
        log.info("Start Time: {}", df.format(new Date()));
    }

    /**
     * dataProvider：并行运行的数据源 <br/>
     * threadPoolSize：多少个线程运行，前提invocationCount非空 <br/>
     * invocationCount：每个线程调用的次数 <br/>
     */
    @Test(enabled=true, dataProvider="testdp", threadPoolSize=2, invocationCount=5)
    public void test(String dpNumber) throws InterruptedException {
        log.info("Current Thread Id: {}, Dataprovider number: {}", Thread.currentThread().getId(), dpNumber);
        Thread.sleep(5000);
    }

    @DataProvider(name = "testdp", parallel = true)
    public Object[][] testdp(){

        return new Object[][]{
                {"1"},
                {"2"}
        };
    }

    @AfterClass
    public void afterClass(){
        log.info("End Time: {}", df.format(new Date()));
    }
}
