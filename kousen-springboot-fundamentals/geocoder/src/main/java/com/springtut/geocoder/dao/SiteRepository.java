package com.springtut.geocoder.dao;

import com.springtut.geocoder.entities.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Integer>{
}
