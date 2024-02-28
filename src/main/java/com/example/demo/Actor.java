package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "actor")
public class Actor {
    public Actor() {
        this.filmsStarringActor = new HashSet<>();
    }

    public Actor(int actorID, String firstName, String lastName, Set<Film> filmsStarringActor) {
        this.actorID = actorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.filmsStarringActor = filmsStarringActor;
    }

    @Id //shows that attribute is primary key
    @Column(name = "actor_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //tells new actor that value is auto generated
    private int actorID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    public int getActorID() {
        return actorID;
    }

    public void setActorID(int actorID) {
        this.actorID = actorID;
    }

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

    public void setFilmsStarringActor(Set<Film> filmsStarringActor) {
        this.filmsStarringActor = filmsStarringActor;
    }

    public Set<Film> getFilmsStarringActor() {
        return filmsStarringActor;
    }

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(name = "actor_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id"))
    Set<Film> filmsStarringActor;
}
