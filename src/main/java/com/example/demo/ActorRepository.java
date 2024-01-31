package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
    @Query(value = "SELECT * FROM actor WHERE first_name LIKE %:name% OR last_name LIKE %:name%", nativeQuery = true)
    Set<Actor> findByFirstNameOrLastNameContaining(String name);

    @Query(value = "SELECT * FROM actor WHERE first_name = :firstName AND last_name = :lastName",nativeQuery = true)
    Actor findByFirstNameAndLastName(String firstName, String lastName);

    @Query(value = "SELECT * FROM actor WHERE first_name = :firstOrLastName OR last_name = :firstOrLastName", nativeQuery = true)
    Set<Actor> findByFirstName(String firstOrLastName);

//    @Query(value = "SELECT * FROM actor WHERE last_name = :lastName", nativeQuery = true)
//    Set<Actor> findByLastName(String lastName);
}
