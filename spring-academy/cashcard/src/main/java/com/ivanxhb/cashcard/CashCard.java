package com.ivanxhb.cashcard;

import org.springframework.data.annotation.Id;

//import jakarta.persistence.Id; // Whats the difference between the two?

public record CashCard(@Id Long id, Double amount) { // Note that we require the @Id annonation for the CrudRepo interface to recognize it as such.
}
