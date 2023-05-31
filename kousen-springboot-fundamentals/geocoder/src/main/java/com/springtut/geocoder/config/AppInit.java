package com.springtut.geocoder.config;

import com.springtut.geocoder.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner{

	private final SiteService service;
	
	public AppInit(SiteService service) {
		this.service = service;
	}
	
	// Delegating to the SiteService object and calling
	// The DB initialization variable in that class
	@Override
	public void run(String... args) {
		service.initializeDatabase();
	}
}