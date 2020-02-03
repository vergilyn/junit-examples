package com.vergilyn.examples.testng.groups;

import org.testng.annotations.Test;

/**
 * <a href="https://testng.org/doc/documentation-main.html">5.2 - Test groups</a>
 * @author vergilyn
 * @date 2020-02-03
 */
public class Official_Groups_1_Testng extends AbstractGroupsTestng {

    @Test(groups = { "functest", "checkintest" })
    public void testMethod1() {
        print();
    }

    @Test(groups = {"functest", "checkintest"} )
    public void testMethod2() {
        print();
    }

    @Test(groups = { "functest" })
    public void testMethod3() {
        print();
    }

    @Test(groups = { "checkintest" })
    public void testMethod4() {
        print();
    }
}
