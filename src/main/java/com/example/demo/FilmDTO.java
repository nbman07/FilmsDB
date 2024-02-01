package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class FilmDTO {
    private String title;
    List<ActorDTO> actors = new ArrayList<>();

    public FilmDTO() {
    }

    public FilmDTO(Film film) {
        this.title = film.getTitle();
        for (Actor actor : film.getActorsStarringInFilm()) {
            ActorDTO actorDTO = new ActorDTO();
            actorDTO.setFirstName(actor.getFirstName());
            actorDTO.setLastName(actor.getLastName());
            actors.add(actorDTO);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
