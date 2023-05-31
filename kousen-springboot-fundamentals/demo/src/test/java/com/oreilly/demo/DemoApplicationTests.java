package com.oreilly.demo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.oreilly.demo.json.Greeting;


@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private ApplicationContext context; // interface. 
	
	@Test
	void contextLoads() {
		System.out.println(context.getClass().getName());
		int count = context.getBeanDefinitionCount();
		System.out.println("There are " + count + " beans loaded" );
		
		Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
	}
	
	@Test
	void noGreetingInAppCtx() {
		Greeting greeting = context.getBean(Greeting.class);
	}
	
	
	

}
