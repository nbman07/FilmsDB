package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@RestController
@RequestMapping("/home")
@CrossOrigin
public class DemoApplication {

    @Autowired
    private ActorRepository actorRepo;
    @Autowired
    private FilmRepository filmRepo;
    @Autowired
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

    // Find actor's films
    @GetMapping("/actor/{id}/films")
    public Set<Film> getActorFilmsByID(@PathVariable("id") int actorID) {
        return actorRepo.findById(actorID)
                        .orElseThrow(() -> new ResourceAccessException("Actor not found."))
                .getFilmsStarringActor();
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

    // Find actors based on given film
    @GetMapping("film/{id}/actors")
    public List<ActorDTO> findActorsByFilm(@PathVariable("id") int filmID) {
        final Film film = filmRepo.findById(filmID).orElseThrow(()->new ResourceAccessException("Film not found with id: "+ filmID));

        FilmDTO filmDTO = new FilmDTO(film, "a");

        return filmDTO.actors;
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
    @GetMapping("/category/{id}/films")
    public List<FilmDTO> findFilmsByCategory(@PathVariable("id") int categoryID) {
        final Category category = categoryRepo.findById(categoryID).orElseThrow(()->new ResourceAccessException("Category not found with id: "+categoryID));

        CategoryDTO categoryDTO = new CategoryDTO(category);
        return categoryDTO.getFilms();
    }

    //Find films based on an actor
//    @GetMapping("actor/{id}/films")
//    public List<FilmDTO> findFilmsByActor(@PathVariable("id") int actorID) {
//        final Actor actor = actorRepo.findById(actorID).orElseThrow(() -> new ResourceAccessException("Actor not found with id: " + actorID));
//
//        ActorDTO actorDTO = new ActorDTO(actor);
////        return (Iterable<ActorDTO>) ResponseEntity.ok(actorDTO);
//        return actorDTO.getFilms();
//    }

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
    // Find category based on ID
    @GetMapping("/category/id={id}")
    public Category findCategoryByID(@PathVariable("id") int categoryID) {
        return categoryRepo.findCategoryByCategoryID(categoryID);
    }

    // Find categories that contain x in the name
    @GetMapping("category/search/containing/{name}")
    public Set<Category> findCategoryByNameContaining(@PathVariable("name") String name) {
        return categoryRepo.findByCategoryNameContaining(name);

    }

    // Find category/categories of a film
    @GetMapping("/film/{id}/categories")
    public List<CategoryDTO> findCategoriesByFilm(@PathVariable("id") int filmID) {
        final Film film = filmRepo.findById(filmID).orElseThrow(()->new ResourceAccessException("Film not found with id: " + filmID));

        FilmDTO filmDTO = new FilmDTO(film,"c");
        return filmDTO.getCategories();
    }


    /******* POST MAPPING *******/

    /******* ACTOR *******/

    // Add a new actor
    @PostMapping("add/actor")
    public Actor addNewActor(@RequestBody Actor actor) {
        return actorRepo.save(actor);
    }

    /******* FILM *******/

    // Add a new film
    @PostMapping("add/film")
    public Film addNewFilm(@RequestBody Film newFilm) {
        return filmRepo.save(newFilm);
    }

    /******* CATEGORY *******/
    // Add a new category
    @PostMapping("add/category")
    public Category addNewCategory(@RequestBody Category newCategory) {
        return categoryRepo.save(newCategory);
    }

    // Add a category to a film and add film to a category
    @PostMapping("film/{filmID}/addCategory/{categoryID}")
    public void addCategoryToFilm(@PathVariable("filmID") int filmID, @PathVariable("categoryID") int categoryID) {
        Category category = categoryRepo.findCategoryByCategoryID(categoryID);
        Film film = filmRepo.findById(filmID).orElseThrow(()->new ResourceAccessException("Film not found with id: "+filmID));
        Set<Category> newListOfCategories;
        try {
            newListOfCategories = film.getCategoriesOfFilms();
        }catch (Exception e) {
            System.out.println("List is null.");
            newListOfCategories = new HashSet<Category>();
        }
        newListOfCategories.add(category);
        film.setCategoriesOfFilms(newListOfCategories);

        filmRepo.save(film);

        Set<Film> newListOfFilms;
        try {

        newListOfFilms = category.getFilmsOfCategory();
        } catch (Exception e) {
            System.out.println("List is null.");
            newListOfFilms = new HashSet<Film>();
        }
        newListOfFilms.add(film);
        category.setFilmCategories(newListOfFilms);

        categoryRepo.save(category);
    }
    // Add an actor to a film -> add a film to an actor
    @PostMapping("film/{filmID}/addActor/{actorID}")
    public void addActorToFilm(@PathVariable("filmID") int filmID, @PathVariable("actorID") int actorID) {
        Actor actor = actorRepo.findById(actorID).orElseThrow(()-> new ResourceAccessException("Actor not found with id: "+actorID));
        Film film = filmRepo.findById(filmID).orElseThrow(()->new ResourceAccessException("Film not found with id: "+filmID));
        Set<Actor> newListOfActors;
        try {
            newListOfActors = film.getActorsStarringInFilm();
        }catch (Exception e) {
            System.out.println("List is null.");
            newListOfActors = new HashSet<Actor>();
        }
        newListOfActors.add(actor);
        film.setActorsStarringInFilm(newListOfActors);

        filmRepo.save(film);

        Set<Film> newListOfFilms;
        try {
            newListOfFilms = actor.getFilmsStarringActor();
        } catch (Exception e) {
            System.out.println("List is null.");
            newListOfFilms = new HashSet<Film>();
        }
        newListOfFilms.add(film);
        actor.setFilmsStarringActor(newListOfFilms);

        actorRepo.save(actor);
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

    // Delete an actor given name
    @DeleteMapping("delete/actor/{firstName}/{lastName}")
    public void deleteActor(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        actorRepo.delete(actorRepo.findByFirstNameAndLastName(firstName, lastName));
    }
    // Delete an actor given ID
    @DeleteMapping("delete/actor/{id}")
    public void deleteActorWithID(@PathVariable("id")  int actorID) {
        actorRepo.delete(actorRepo.findById(actorID).orElseThrow());
    }

    // Delete a film given ID
    @DeleteMapping("delete/film/{id}")
    public void deleteFilmWithID(@PathVariable("id") int filmID) {
        filmRepo.delete(filmRepo.findById(filmID).orElseThrow());
    }

    // Delete a category given ID
    @DeleteMapping("delete/category/{id}")
    public void deleteCategoryWithID(@PathVariable("id") int categoryID) {
        categoryRepo.delete(categoryRepo.findById(categoryID).orElseThrow());
    }

}
