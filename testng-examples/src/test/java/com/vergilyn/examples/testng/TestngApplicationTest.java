package com.vergilyn.examples.testng;

import java.util.List;

import com.vergilyn.examples.common.JavaTestApplication;
import com.vergilyn.examples.common.domain.UserEntity;
import com.vergilyn.examples.common.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * TestNG并发测试<br/>
 * <ul>
 *     <li>{@link AbstractTransactionalTestNGSpringContextTests}: 自动回滚事务。多数据源处理参考{@link AbstractTestngRollback}</li>
 *     <li>{@link AbstractTestNGSpringContextTests}: 自动提交事务。暂时不知道怎么回滚事务，{@link Transactional}、{@link Rollback}并不能回滚事务</li>
 * </ul>
 * @author VergiLyn
 * @blog http://www.cnblogs.com/VergiLyn/
 * @date 2018/4/16
 */
@SpringBootTest(classes= JavaTestApplication.class)
public class TestngApplicationTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private UserService testService;

    /**
     * dataProvider：并行运行的数据源 <br/>
     * threadPoolSize：多少个线程运行，前提invocationCount非空 <br/>
     * invocationCount：每个线程调用的次数 <br/>
     * @param username
     */
    @Test(enabled=true, dataProvider="testdp",threadPoolSize=2, invocationCount=5)
    public void testService(String username){
        testService.save(username);

        List<UserEntity> users = testService.get(username);
        System.out.println(users);
    }


    /**
     * 如果testService(String, int, Date) -> new Object[][]{{"username-01", 124, new Date()}, {"username-02", 248, new Date()}};
     * @return
     */
    @DataProvider(name = "testdp", parallel = true)
    public static Object[][] testdp() {

        return new Object[][]{{"username-01"}, {"username-02"}};
    }
}
