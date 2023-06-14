package com.ivanhb.movies;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<Movie, ObjectId> {

    // Realize that Spring makes this possible
    // This via Automatic queries from Object property names
    // Realize that Spring and Spring Data MongoDB does a lot of the heavy lifting.
    // It possible to accomplish this functionally because the data properties are unique.
    // What is the underlying implementation like?
    Optional<Movie> findMovieByImdbId(String imdbId); // must be of the same type?
}
