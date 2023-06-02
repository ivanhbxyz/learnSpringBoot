package com.ivanxhb.cashcard.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ivanxhb.cashcard.CashCard;

public interface CashCardRepository extends CrudRepository <CashCard, Long>{ // we indicate that CashCard's id is a Long
    
}
