package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class ActorDTO {
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<FilmDTO> getFilms() {
        return films;
    }

    private List<FilmDTO> films = new ArrayList<>();

    public ActorDTO() {
    }

    public ActorDTO(Actor actor) {
        this.firstName = actor.getFirstName();
        this.lastName = actor.getLastName();
        for (Film film : actor.getFilmsStarringActor()) {
            FilmDTO filmDTO = new FilmDTO();
            filmDTO.setTitle(film.getTitle());
            films.add(filmDTO);
        }
    }
}