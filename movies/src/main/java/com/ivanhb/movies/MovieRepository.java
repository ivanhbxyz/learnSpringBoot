package com.ivanhb.movies;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<Movie, ObjectId> {

    // Realize that Spring makes this possible
    // This via Automatic queries from Object property names
    Optional<Movie> findMovieByImdbId(String imdbId); // must be of the same type?
}
