package com.ivanxhb.cashcard;

import jakarta.persistence.Id;

public record CashCard(@Id Long id, Double amount) { // Note that we require the @Id annonation for the CrudRepo interface to recognize it as such.

}
