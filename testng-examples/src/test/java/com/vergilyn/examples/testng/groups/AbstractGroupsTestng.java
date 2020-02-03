package com.vergilyn.examples.testng.groups;

import org.testng.annotations.Test;

/**
 * @author vergilyn
 * @date 2020-02-03
 */
@Test(groups = "class-level-groups")  // testng可以定义class级别的groups，其下的所有方法默认属于该group
public abstract class AbstractGroupsTestng {

    protected void print(){

        System.out.printf("class: %s, method: %s",
                this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());

        // 或者 new Exception().getStackTrace()[0].getMethodName();  // 获得当前的方法名
    }
}
