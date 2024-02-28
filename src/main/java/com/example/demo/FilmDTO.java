package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class FilmDTO {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String title;

    public void setActors(List<ActorDTO> actors) {
        this.actors = actors;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    List<ActorDTO> actors = new ArrayList<>();
    List<CategoryDTO> categories = new ArrayList<>();

    public List<ActorDTO> getActors() {
        return actors;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public FilmDTO() {
    }

    public FilmDTO(Film film, String actorOrCategory) {
        this.title = film.getTitle();
        if (actorOrCategory.equalsIgnoreCase("a")) {
            for (Actor actor : film.getActorsStarringInFilm()) {
                ActorDTO actorDTO = new ActorDTO();
                actorDTO.setFirstName(actor.getFirstName());
                actorDTO.setLastName(actor.getLastName());
                actors.add(actorDTO);
            }
        } else if (actorOrCategory.equalsIgnoreCase("c")) {
            for (Category category : film.getCategoriesOfFilms()) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setName(category.getCategoryName());
                categories.add(categoryDTO);
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
