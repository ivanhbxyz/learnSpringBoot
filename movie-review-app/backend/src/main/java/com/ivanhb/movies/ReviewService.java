package com.ivanhb.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate; // MongoTemplates are a way to pass queries to Mongo

    public Review createReview(String reviewBody, String imdbId) {
        //Review review = new Review(reviewBody);
        //reviewRepository.insert(review);
        Review review = reviewRepository.insert(new Review(reviewBody));

        // LEARN MORE ABOUT MONGO TEMPLATE REQUEST CONSTRUCTION.
        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();

        return review;
    }
}
