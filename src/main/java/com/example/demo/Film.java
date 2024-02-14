package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "film")
public class Film {
    public Film() {
    }

    public Film(int filmID, String title, String description, int languageID, String rating, int length) {
        this.filmID = filmID;
        this.title = title;
        this.description = description;
//        this.releaseYear = releaseYear;
        this.languageID = languageID;
        this.rating = rating;
        this.length = length;
//        this.specialFeatures = specialFeatures;
    }

    @Id
    @Column(name = "film_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int filmID;

    public int getFilmID() {
        return filmID;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public int getReleaseYear() {
//        return releaseYear;
//    }
//
//    public void setReleaseYear(int releaseYear) {
//        this.releaseYear = releaseYear;
//    }

    public int getLanguageID() {
        return languageID;
    }

    public void setLanguageID(int languageID) {
        this.languageID = languageID;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

//    public List<String> getSpecialFeatures() {
//        return specialFeatures;
//    }
//
//    public void setSpecialFeatures(List<String> specialFeatures) {
//        this.specialFeatures = specialFeatures;
//    }

    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    //    @Column(name = "release_year")
//    private int releaseYear;
    @Column(name = "language_id")
    private int languageID;
    @Column(name = "rating")
    private String rating;
    @Column(name = "length")
    private int length;
    //    @Column(name = "special_features")
//    private List<String> specialFeatures;
    @ManyToMany(mappedBy = "filmsStarringActor")
    Set<Actor> actorsStarringInFilm;

    public void setCategoriesOfFilms(Set<Category> categoriesOfFilms) {
        this.categoriesOfFilms = categoriesOfFilms;
    }

    @ManyToMany(mappedBy = "filmsOfCategory")
    Set<Category> categoriesOfFilms;

    public void setActorsStarringInFilm(Set<Actor> actorsStarringInFilm) {
        this.actorsStarringInFilm = actorsStarringInFilm;
    }

    public Set<Actor> getActorsStarringInFilm() {
        return actorsStarringInFilm;
    }

    public Set<Category> getCategoriesOfFilms() {
        return categoriesOfFilms;
    }
}

