package com.ivanxhb.cashcard.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ivanxhb.cashcard.CashCard;


// We find that as more users are added to the application
// We need to filter the responses returned to the users
// Otherwise users would be able to see each other's data and this is not secure
//
// We add Page and PageRequest
//public interface CashCardRepository extends CrudRepository<CashCard, Long>,

public interface CashCardRepository extends CrudRepository<CashCard, Long>, 
PagingAndSortingRepository<CashCard, Long> {

    CashCard findByIdAndOwner(Long id, String owner);
    Page<CashCard> findByOwner(String owner, PageRequest amount);
    
}

/*
 * 
 * See: Spring Data Query Methods to see about implementing your own queries
 */