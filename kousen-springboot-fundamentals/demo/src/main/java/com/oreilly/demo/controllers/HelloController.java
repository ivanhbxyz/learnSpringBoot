package com.oreilly.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


// Controllers are responsible for handling/responding to requests
// You want Spring to manage this "Bean"
// use the @Controller Spring annotation

// What is ComponentScan?
// 	Spring will scan through the directories and look for components
@Controller
public class HelloController {
	
	// Public method. it responds to the http GET request on /hello
	// On this request: 0.0.0.0:8080/hello?name=ivan
	@GetMapping("/hello")
	public String sayHello(
			@RequestParam(value="name", required=false,
					defaultValue="World")String name, Model model) {
		
		// How to query using @RequestParam?
		
		// Where does model come from?
		// Model targets the "View" layer
		// REALIZE: That the "View" layer
		// is actually the "View" "Model" that is the .html Thymeleaf template
		// Spring will look to the html and replace the name parameter into
		// the "user" key.
		model.addAttribute("user", name); // adds the key pair, "user": name
		
		
		// Why is there a had coded "hello"?
		// Note that this is a command to Spring
		// That tells it to look for hello.html
		// Spring looks for this file in /src/main/resources/templates
		// Note: That we use templates because it is a 
		// page that will be modified depending on the
		// parameter the user provides
		return "hello"; // The way out
	}

}
