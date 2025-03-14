package com.example.Forum_App;

//importing necessary Jpa annotations and utility classes
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

//annotation to mark this class as a JPA entity(represent a table in database)
@Entity
public class User {

    //annotation as primary key
    @Id
    //strategy to specify primary key value automatically generated using an identity column in database
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //field to store username from users
    private String username;

    //defines a one-to-many relationship between user and message. message entity has foreign key pointed to user.
    //cascade all operations to related message entities
    //orphanRemoval ensures that íf a message is removed from the list also remove it from database
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    // Getters och setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}