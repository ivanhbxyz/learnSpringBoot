package com.ivanxhb.cashcard.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ivanxhb.cashcard.CashCard;

public interface CashCardRepository extends CrudRepository<CashCard, Long>,
PagingAndSortingRepository<CashCard, Long> {
    
}
