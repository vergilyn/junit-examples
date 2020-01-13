package com.vergilyn.examples.testng.junit;

import java.util.List;

import com.vergilyn.examples.common.JavaTestApplication;
import com.vergilyn.examples.common.domain.UserEntity;
import com.vergilyn.examples.common.service.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@SpringBootTest(classes= JavaTestApplication.class)
public class JUnit4ApplicationTest {
    @Autowired
    private UserService testService;
    private static final String NAME = "vergilyn";

    @Test
    public void testService(){
        testService.save(NAME);

        List<UserEntity> users = testService.get(NAME);
        System.out.println(users);
    }

    /**
     * 回滚事务
     */
    @Test
    @Transactional
    @Rollback(true)
    public void rollback(){
        System.out.println("transaction rollback >>>>>>>>>>>>>>>>>>>>>>>> " + testService.save(NAME));
    }
}