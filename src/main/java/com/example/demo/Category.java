package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "category")
public class Category {
    public Category() {
    }
    @Id //shows that attribute is primary key
    @Column(name = "category_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //tells new actor that value is auto generated
    private int categoryID;
    @Column(name = "name")
    private String categoryName;

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setFilmCategories(Set<Film> filmsOfCategory) {
        this.filmsOfCategory = filmsOfCategory;
    }

    public Set<Film> getFilmsOfCategory() {
        return filmsOfCategory;
    }

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "film_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id"))
    Set<Film> filmsOfCategory;
}
