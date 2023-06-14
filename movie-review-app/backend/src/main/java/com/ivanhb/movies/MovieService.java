package com.ivanhb.movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired // we want the framework to instantiate it for us
    private MovieRepository movieRepository;
    // Database access methods
    public List<Movie> allMovies() {
        return movieRepository.findAll(); // findAll() part of mongoRepo interface
    }

    // Realize that Optional tell us that it is possible to return a Null object upon calling this method.
    public Optional<Movie> singleMovie(String imdbId) {
        return movieRepository.findMovieByImdbId(imdbId);
    }
}
