package com.example.Forum_App;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//marking the class as a JPA entity which will store it into the database
@Entity
public class Channel {

    //marking this as primarykey
    @Id
    //strategy to automatically generate the value for primary key in database when a new post is created
    //(automatically sets an uniq ID for every new channel/message)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    //annotation to define a one-to-many relationship, to contain several messages.
    //mapping the message-list to the com.example.Forum_App.Channel
    @ElementCollection
    private List<String> messages = new ArrayList<>();

    //constructor, getters och setters
    public Channel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Channel() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        messages.add(message);
    }
}
