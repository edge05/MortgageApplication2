package com.zos.java.Example.Test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.zos.java.Example.HelloWorld;

public class HelloWorldTest {

	@Test
	public void testSayHelloWorld() {
        HelloWorld classUnderTest = new HelloWorld();
        assertTrue("sayHelloWorld should return 'true'", classUnderTest.sayHelloWorld());
        /** fail("Not yet implemented"); */
	}

}
