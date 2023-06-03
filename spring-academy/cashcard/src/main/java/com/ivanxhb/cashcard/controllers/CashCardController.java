package com.ivanxhb.cashcard.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import com.ivanxhb.cashcard.CashCard;
import com.ivanxhb.cashcard.repositories.CashCardRepository;


@RestController // Tells Spring that this is a RestController component
@RequestMapping("/cashcards") // Tells Spring that instances of this class is the endpoint to  HTTP requests to /cashcard.

public class CashCardController {
    private CashCardRepository cashCardRepository;
    
    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    

    /* 
	@GetMapping("/{requestedID}") // @GetMapping handles HTTP GET requests. /{requestedID} signals that we Respond to those requests
	public ResponseEntity<CashCard> findById(@PathVariable Long requestedID) { // Realize that the return type is a ResponseEntity, that is a CashCard objects
        // Explain: what @PathVariable does.
        
        //Optional<CashCard> cashCardOptional = cashCardRepository.findById(requestedID);
        if(requestedID.equals(99L)) {
            CashCard cashCard = new CashCard(99L, 123.45);
            return ResponseEntity.ok(cashCard);
        }
        else{
            return ResponseEntity.notFound().build(); // why is build required?
        }

	}
    */

    @GetMapping("/{requestedId}")
    public ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
        Optional<CashCard> cashCardOptional = cashCardRepository.findById(requestedId);
        if (cashCardOptional.isPresent()) {
            return ResponseEntity.ok(cashCardOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb) {

        /*
         Spring Data's CrudRepository provides methods that support creating, reading, updating, and deleting data from a data store. 
         cashCardRepository.save(newCashCardRequest): saves a new CashCard object, and returns the saved object with a unique id provided by the database.
        */
        CashCard savedCashCard = cashCardRepository.save(newCashCardRequest);
        
        URI locationOfNewCashCard = ucb // includes a deserialized version of the created object
            .path("cashcards/{id}") // matches out endpoint design
            .buildAndExpand(savedCashCard.id())// utilize the created object. instance of CashCard
            .toUri();

            return ResponseEntity.created(locationOfNewCashCard).build(); // return 201 CREATED with the correct Location header.
        }
}