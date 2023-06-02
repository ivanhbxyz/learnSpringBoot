package com.ivanxhb.cashcard.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivanxhb.cashcard.CashCard;
import com.ivanxhb.cashcard.repositories.CashCardRepository;

@RestController // Tells Spring that this is a RestController component
@RequestMapping("/cashcards") // Tells Spring that instances of this class is the endpoint to  HTTP requests to /cashcard.

public class CashCardController {

    private CashCardRepository cashCardRepository;

    public CashCardController( CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }
	
	@GetMapping("/{requestedID}") // @GetMapping handles HTTP GET requests. /{requestedID} signals that we Respond to those requests
	public ResponseEntity<CashCard> findById(@PathVariable Long requestedID) { // Realize that the return type is a ResponseEntity, that is a CashCard objects

        // Explain: what @PathVariable does.
        
        if(requestedID.equals(99L)) {
            CashCard cashCard = new CashCard(99L, 123.45);
            return ResponseEntity.ok(cashCard);
        }
        else{
            return ResponseEntity.notFound().build(); // why is build required?
        }

	}
}