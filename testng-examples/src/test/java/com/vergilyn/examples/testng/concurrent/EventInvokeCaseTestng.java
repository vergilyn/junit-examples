package com.vergilyn.examples.testng.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class EventInvokeCaseTestng {

	private final AtomicInteger _index = new AtomicInteger(0);

	@Test(singleThreaded = true, invocationCount = 5)
	public void test(){
		System.out.printf("[vergilyn][%d] >>>> #test() \n", _index.incrementAndGet());
	}

	@AfterTest
	public void afterTest(){
		System.out.printf("[vergilyn][%d] >>>> #afterTest() \n", _index.get());
	}

	@AfterMethod
	public void afterMethod(){
		System.out.printf("[vergilyn][%d] >>>> #afterMethod() \n", _index.get());
	}

	@AfterClass
	public void afterClass(){
		System.out.printf("[vergilyn][%d] >>>> #afterClass() \n", _index.get());
	}
}
