package com.example.Forum_App;

//importing necessary JPA annotation and Jackson annotations for json handling
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

//annotation to mark this class as a JPA entity(represent a table in database)
@Entity
public class Message {

    //annotation as primary key
    @Id
    //strategy to specify primary key value automatically generated using an identity column in database
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    //field to store content of message
    private String content;

    //annotation to define many-to-one relationship between message and user
    @ManyToOne
    //joinColumn to specify foreign key column in the message table that references to user table
    @JoinColumn(name = "user_id")
    //annotation to prevent infinite recursion then serializing to json
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    @JsonBackReference("channel-messages")
    private Channel channel;


    // Getters och setters
    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {

        this.content = content;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {

        this.user = user;
    }

    public Channel getChannel() {

        return channel;
    }

    public void setChannel(Channel channel) {

        this.channel = channel;
    }
}