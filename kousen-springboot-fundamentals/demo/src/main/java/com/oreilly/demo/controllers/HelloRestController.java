package com.oreilly.demo.controllers;

import com.oreilly.demo.json.Greeting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// Explain the RestController.
// RestController automatically serialize the response, that is returns JSON data
@RestController
public class HelloRestController {
	
	@GetMapping("/rest")
	public Greeting greet(
					@RequestParam(required=false, defaultValue="World")String name) {
		
		return new Greeting("Hello, " + name + " !");
	}
}
