package com.example.demo;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

//import static junit.framework.TestCase.*;

public class StepDefs extends SpringIntegrationTest {
    private final String expectedFirstName = "First";
    private final String expectedLastName = "Last";

    Actor actor;
    ActorDTO actorDTO;
    ActorRepository actorRepo;

    Film film;
    FilmDTO filmDTO;
    @Autowired
    FilmRepository filmRepo;
    Category category;
    CategoryDTO categoryDTO;
    CategoryRepository categoryRepo;

    /********************************************/

    @Given("the Actor has values for first name and last name")
    public void actor_has_values_for_first_and_last_name() {
        actor = new Actor();
        actor.setFirstName(expectedFirstName);
        actor.setLastName(expectedLastName);
    }

    @When("I create a new Actor")
    public void new_actor_is_created() {
    }

    @Then("Actor should have first and last name")
    public void actor_should_have_first_and_last_name() {
        assertEquals(expectedFirstName, actor.getFirstName());
        assertEquals(expectedLastName, actor.getLastName());
    }

    /********************************************/

    @When("I call /actor/{int}")
    public void call_actor_and_id(int actorID) throws Throwable {
        executeGet("http://localhost:8080/home/actor/"+actorID);
    }
    @Then("The actor with id:{int} is returned.")
    public void actor_with_id_is_returned(int actorID) throws Throwable {
    }

}
