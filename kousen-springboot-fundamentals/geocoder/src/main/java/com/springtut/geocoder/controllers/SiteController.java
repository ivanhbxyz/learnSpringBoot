package com.springtut.geocoder.controllers;

import com.springtut.geocoder.entities.Site;
import com.springtut.geocoder.services.SiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController // meaning Spring automatically serializes output into JSON

// Request Mapping on entire class
// Meaning anywhere the location is not specified(within), then this is the default
@RequestMapping("/sites") // class wide requestMapper
public class SiteController {
	private final SiteService service;
	
	public SiteController(SiteService service) {
		this.service = service;
	}
	
	@GetMapping
	public List<Site> findAll() {
		return service.getAllSites();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Site> findById(@PathVariable Integer id) {
		// What is a ResponseEntity?
		return ResponseEntity.of(service.findSiteById(id));
		// Note that the .of method automatically returns http status updates upon a result
	}
	
	@PostMapping
	public ResponseEntity<Site> saveSite(@RequestParam String address) {
		Site site = service.saveSite(address);
		
		// How to build the new URL for the newly saved Site
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(site.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(site);
		// Research Spring MVC
	}
	
	
	
}