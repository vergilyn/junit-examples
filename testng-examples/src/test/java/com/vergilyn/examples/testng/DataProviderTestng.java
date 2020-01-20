package com.vergilyn.examples.testng;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author VergiLyn
 * @date 2020-01-20
 */
@Slf4j
public class DataProviderTestng {

    @Test(dataProvider = "testdp")
    public void dataProvider(int id, String name){
        log.info("parameters: id = {}, name = {}", id, name);
    }

    /**
     * 如果testService(String, int, Date) -> new Object[][]{{"username-01", 124, new Date()}, {"username-02", 248, new Date()}};
     * @return
     */
    @DataProvider(name = "testdp")
    public Object[][] testdp(){

        return new Object[][]{
                {1, "id-01"},
                {2, "id-02"}
            };
    }
}
