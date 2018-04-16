package com.vergilyn.examples.junit;

import java.util.List;

import com.vergilyn.examples.junit.domain.TestUser;
import com.vergilyn.examples.junit.service.TestService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * junit4或springboot的junit单元测试
 * @author VergiLyn
 * @blog http://www.cnblogs.com/VergiLyn/
 * @date 2018/4/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=TestApplication.class)
public class JUnit4ApplicationTest {
    @Autowired
    private TestService testService;
    @Value("${app.name}")
    private String appName;

    @Test
    public void testService(){
        testService.save(appName);

        List<TestUser> users = testService.get(appName);
        System.out.println(users);
    }

    /**
     * 回滚事务
     */
    @Test
    @Transactional
    @Rollback(true)
    public void rollback(){
        System.out.println("transaction rollback >>>>>>>>>>>>>>>>>>>>>>>> " + testService.save(appName));
    }
}