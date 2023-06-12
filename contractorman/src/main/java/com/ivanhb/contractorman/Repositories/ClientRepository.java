package com.ivanhb.contractorman.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivanhb.contractorman.Entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{
}
