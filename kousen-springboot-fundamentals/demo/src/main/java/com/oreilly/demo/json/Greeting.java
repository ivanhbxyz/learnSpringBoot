package com.oreilly.demo.json;

public class Greeting {
	
	private final String message;
	
	public Greeting() {
		this.message = "";
	}
	
	public Greeting(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return "Greeting{" +
				"messag='" + message + "/'" +
				"}";
	}
}
