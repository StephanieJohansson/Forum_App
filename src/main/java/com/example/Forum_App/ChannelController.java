package com.example.Forum_App;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//setting the class as a REST-controller to deal with HTTP requests
@RestController
//defines base URL for every class in this controller - setting endpoint to /Channels
@RequestMapping("/Channels")
public class ChannelController {

    //dependency injection (DI) to automatically handle the creation of an instance to this class
    @Autowired
    private ChannelRepository channelRepository;

    //mapping GET-requests to this method, returning the list of all channels from database
    @GetMapping
    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }
}
