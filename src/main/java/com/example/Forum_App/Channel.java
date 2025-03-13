package com.example.Forum_App;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference//handles serializations of messages
    @JsonIgnore
    private List<Message> messages = new ArrayList<>();

    //constructor, getters och setters
   // public Channel(Long id, String name) {
     //   this.id = id;
       // this.name = name;
    //}

   // public Channel() {

    //}

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {

        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessages() {
        return messages;
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
        message.setChannel(this);
    }
}
