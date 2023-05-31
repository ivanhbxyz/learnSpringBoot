package com.oreilly.demo.json;

import java.util.List;

public class AstroResult {
	
	private String message;
	private int number;
	private List<Assignment> people;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getNumber() {
		return number;
	}

	public List<Assignment> getPeople() {
		
		return people;
	}

}
