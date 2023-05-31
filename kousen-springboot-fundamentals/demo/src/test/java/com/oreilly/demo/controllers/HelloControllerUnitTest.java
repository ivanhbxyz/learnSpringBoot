package com.oreilly.demo.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import static org.junit.jupiter.api.Assertions.*;

class HelloControllerUnitTest {

	@Test
	public void sayHello() {
		HelloController controller = new HelloController(); // create instance of your controller
		Model model = new BindingAwareModelMap(); // a Map that implements the model and is aware of the binding
		String result = controller.sayHello("World", model); // Hardcodes the value of "World" as the argument passed to sayHello
		
		assertAll( // will execute all of the included assertions even if all of some fail
				() -> assertEquals("World", model.getAttribute("user")), // assert that the model as a "user" attribute and its name is "Model"
				() -> assertEquals("hello", result) // Assert that the name of the model 
				
				);
	}
}
