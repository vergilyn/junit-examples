package com.vergilyn.examples.testng.groups;

import org.testng.annotations.Test;

/**
 * <a href="https://testng.org/doc/documentation-main.html">5.2 - Test groups</a>
 * <br/>
 * Testng groups支持正则表达式：
 *    TestNG uses regular expressions, and not wildmats. Be aware of the difference
 *    (for example, "anything" is matched by ".*" -- dot star -- and not "*").
 * @author vergilyn
 * @date 2020-02-03
 */
public class Official_Groups_2_Testng extends AbstractGroupsTestng {

    @Test(groups = { "windows.checkintest"})
    public void testWindowsOnly() {
        print();
    }

    @Test(groups = {"linux.checkintest"} )
    public void testLinuxOnly() {
        print();
    }

    @Test(groups = {"windows.functest"})
    public void testWindowsToo() {
        print();
    }

    @Test(groups = {"unix.functest"})
    public void testUnixOnly() {
        print();
    }
}
