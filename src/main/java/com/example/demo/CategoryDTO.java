package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTO {
    private String name;

    public void setFilms(List<FilmDTO> films) {
        this.films = films;
    }

    private List<FilmDTO> films = new ArrayList<>();
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<FilmDTO> getFilms() {
        return films;
    }

    public CategoryDTO() {}

    public CategoryDTO(Category category) {
        this.name = category.getCategoryName();
        for (Film film : category.getFilmsOfCategory()) {
            FilmDTO filmDTO = new FilmDTO(film, "c");
            filmDTO.setId(film.getFilmID());
            filmDTO.setTitle(film.getTitle());
            films.add(filmDTO);
        }
    }
}
