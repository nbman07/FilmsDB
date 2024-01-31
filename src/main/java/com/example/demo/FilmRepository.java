package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface FilmRepository extends JpaRepository<Film,Integer> {
    @Query(value = "SELECT * FROM film", nativeQuery = true)
    Set<Film> getFilmsByActorName(String first_name, String last_name);
    Set<Film> findByTitleContaining(String title);

    @Query(value = "SELECT film.* FROM film,category,film_category WHERE film_category.category_id = category.category_id AND film_category.film_id = film.film_id AND category.name = :categoryName", nativeQuery = true)
    Set<Film> findFilmsByCategory(String categoryName);

//    @Query(value = "SELECT film_id FROM film WHERE title LIKE :filmTitle",nativeQuery = true)
    Film findFilmByTitle(String filmTitle);

}
