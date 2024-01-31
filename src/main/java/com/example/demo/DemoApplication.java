package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
@RestController
@RequestMapping("/home")
@CrossOrigin
public class DemoApplication {

    @Autowired
    private ActorRepository actorRepo;
    private FilmRepository filmRepo;
    private CategoryRepository categoryRepo;

    public DemoApplication(ActorRepository actorRepo, FilmRepository filmRepo, CategoryRepository categoryRepo) {
        this.actorRepo = actorRepo;
        this.filmRepo = filmRepo;
        this.categoryRepo = categoryRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /******* GET MAPPING *******/

    /*******  ACTOR *******/

    // Find all actors
    @GetMapping("/allActors")
    public Iterable<Actor> getAllActors() {
        return actorRepo.findAll();
    }

    // Find actor given ID
    @GetMapping("/actor/{id}")
    public Actor getActorByID(@PathVariable("id") int actorID) {
        return actorRepo.findById(actorID).
                orElseThrow(() -> new ResourceAccessException("Actor not found."));
    }

    // Find actor given first and last name
    @GetMapping("/actor/search-first-and-last/{firstName}/{lastName}")
    public Actor findByFirstNameAndLastName(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        return actorRepo.findByFirstNameAndLastName(firstName, lastName);
    }

    // Find actor given first or last name
    @GetMapping("actor/search-first-or-last/{firstOrLastName}")
    public Set<Actor> findByFirstName(@PathVariable("firstOrLastName") String firstOrLastName) {
        return actorRepo.findByFirstName(firstOrLastName);
    }

    // Find actor with names containing x
    @GetMapping("actor/search/containing/{x}")
    public Set<Actor> findByFirstNameOrLastNameContaining(@PathVariable("x") String match) {
        return actorRepo.findByFirstNameOrLastNameContaining(match);
    }

    /*******  FILM *******/

    // Find all films
    @GetMapping("/allFilms")
    public Iterable<Film> getAllFilms() {
        return filmRepo.findAll();
    }

    // Find film given film ID
    @GetMapping("/film/{id}")
    public Film getFilmByID(@PathVariable("id") int filmID) {
        return filmRepo.findById(filmID).
                orElseThrow(() -> new ResourceAccessException("Film not found."));
    }

    // Find films that contain x in the title
    @GetMapping("film/search/containing/{title}")
    public Set<Film> findByTitle(@PathVariable("title") String title) {
        return filmRepo.findByTitleContaining(title);
    }

    // Find films based on category given
    @GetMapping("/category/{name}/films")
    public Set<Film> findFilmsByCategory(@PathVariable("name") String categoryName) {
        return filmRepo.findFilmsByCategory(categoryName);
    }

    /******* CATEGORY *******/

    // Find all categories
    @GetMapping("/allCategories")
    public Iterable<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    // Find category based on name
    @GetMapping("/category/{name}")
    public Category findCategoryByName(@PathVariable("name") String categoryName) {
        return categoryRepo.findCategoryByCategoryName(categoryName);
    }


    /******* POST MAPPING *******/

    /******* ACTOR *******/

    // Add a new actor
    @PostMapping("add/actor")
    public Actor addActor(@RequestBody Actor actor) {
        return actorRepo.save(actor);
    }

    /******* FILM *******/

    // Add a new film
    @PostMapping("add/film")
    public Film addFilm(@RequestBody Film newFilm) {
        return filmRepo.save(newFilm);
    }

    /******* CATEGORY *******/
    // Add a new category
    @PostMapping("add/category")
    public Category addCategory(@RequestBody Category newCategory) {
        return categoryRepo.save(newCategory);
    }

    /******* PUT MAPPING *******/

    @PutMapping("update/actor/{id}")
    public void updateActor(@PathVariable("id") int actorID, @RequestBody Actor actor) {
        Actor updatedActor = actorRepo.findById(actorID).orElseThrow(() -> new ResourceAccessException("Actor does not exist with ID: " + actorID));

        updatedActor.setFirstName(actor.getFirstName());
        updatedActor.setLastName(actor.getLastName());

        actorRepo.save(updatedActor);
    }

    @PutMapping("update/film/{id}")
    public void updateFilm(@PathVariable("id") int filmID, @RequestBody Film film) {
        Film updatedFilm = filmRepo.findById(filmID).orElseThrow(() -> new ResourceAccessException("Film does not exist with ID: " + filmID));

        updatedFilm.setTitle(film.getTitle());
        updatedFilm.setDescription(film.getDescription());
        updatedFilm.setLanguageID(film.getLanguageID());
        updatedFilm.setLength(film.getLength());
        updatedFilm.setRating(film.getRating());

        filmRepo.save(updatedFilm);
    }

    @PutMapping("update/category/{id}")
    public void updateCategory(@PathVariable("id") int categoryID, @RequestBody Category category) {
        Category updatedCategory = categoryRepo.findById(categoryID).orElseThrow(() -> new ResourceAccessException("Category does not exist with ID: " + categoryID));

        updatedCategory.setCategoryName(category.getCategoryName());

        categoryRepo.save(updatedCategory);
    }

    /******* DELETE MAPPING *******/

    // Delete an actor
    @DeleteMapping("delete/actor/{firstName}/{lastName}")
    public void deleteActor(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        actorRepo.delete(actorRepo.findByFirstNameAndLastName(firstName, lastName));
    }

    // Delete a film
    @DeleteMapping("delete/film/{title}")
    public void deleteFilm(@PathVariable("title") String title) {
        filmRepo.delete(filmRepo.findFilmByTitle(title));
    }

    // Delete a category
    @DeleteMapping("delete/category/{name}")
    public void deleteCategory(@PathVariable("name") String categoryName) {
        categoryRepo.delete(categoryRepo.findCategoryByCategoryName(categoryName));
    }
}
