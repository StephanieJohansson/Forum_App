package com.example.Forum_App;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    //@JsonIgnore// avoid infinite recursion
    private User user;

    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name = "channel_id")
    @JsonBackReference
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