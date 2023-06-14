package com.ivanhb.movies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection="movies") //An annotation for working with NoSQL

@Data // Lombok project annotation that inject the getter and setter methods
@AllArgsConstructor // annonation for constructor
@NoArgsConstructor
public class Movie {
    @Id // What does this do?
    private ObjectId id;

    private String imdbId;
    private String title;
    private String releaseDate;
    private String trailerLink;
    private String poster;
    private List<String> genres;
    private List<String> backdrops;

    // Will cause the DB to ONLY the Ids of the reviews WHILE the actual reviews are in a different collection
    @DocumentReference
    private List<Review> reviewIds;
}
