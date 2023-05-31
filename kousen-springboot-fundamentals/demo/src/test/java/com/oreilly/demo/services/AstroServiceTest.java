package com.oreilly.demo.services;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.oreilly.demo.json.Assignment;
import com.oreilly.demo.json.AstroResult;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AstroServiceTest {
	
	@Autowired
	private AstroService service;
	
	
	@Test
	void getAstronautsRT() {
		AstroResult result = service.getAstronautsRT();
		int number = result.getNumber();
		System.out.println("There are " + number + " people in space");
		List<Assignment> people = result.getPeople();
		
		people.forEach(System.out::println);
		
		
		for(int i = 0;i < people.size(); i++) {
			System.out.println(people.get(i).toString());
		}
		
		assertAll(
				() -> assertTrue(number >= 0),
				() -> assertEquals(number, people.size())
				);
	}
	
	@Test
	void getAstronautsWC() {
		AstroResult result = service.getAstronautsWC();
		int number = result.getNumber();
		System.out.println("There are " + number + " people in space");
		List<Assignment> people = result.getPeople();
		
		people.forEach(System.out::println);
		
		/*
		for(int i = 0;i < people.size(); i++) {
			System.out.println(people.get(i).toString());
		}
		*/
		assertAll(
				() -> assertTrue(number >= 0),
				() -> assertEquals(number, people.size())
				);
	}
	
	

}
