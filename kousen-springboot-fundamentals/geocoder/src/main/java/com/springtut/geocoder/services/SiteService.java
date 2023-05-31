package com.springtut.geocoder.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springtut.geocoder.dao.SiteRepository;
import com.springtut.geocoder.entities.Site;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SiteService {
	private final SiteRepository repository;
	private final GeocoderService geocoderService;
	
	
	@Autowired
	public SiteService(SiteRepository repository, GeocoderService geocoderService) {
		this.repository = repository;
		this.geocoderService = geocoderService;
	}
	
	private Site fillInLatLng(Site site) {
		return geocoderService.updateSiteLatLng(site);
	}
	
	public void initializeDatabase() {
		repository.saveAll(
				List.of(
						fillInLatLng(new Site("Boston, MA")),
						fillInLatLng(new Site("Framingham, MA")),
						fillInLatLng(new Site("Waltham, MA")))
						).forEach(System.out::println);
	}
	
	public List<Site> getAllSites() {
		return repository.findAll();
	}
	
	public Site saveSite(String address) {
		return repository.save(fillInLatLng(new Site(address)));
	}
	
	public Optional<Site> findSiteById(Integer id) {
		return repository.findById(id);
	}
}
