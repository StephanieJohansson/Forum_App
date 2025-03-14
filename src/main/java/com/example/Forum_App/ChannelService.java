package com.example.Forum_App;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//annotation to mark this class as a Spring service component
@Service
public class ChannelService {

    //dependency injection of ChannelService used to interact with database for channel entities
    ChannelRepository channelRepo;

    //constructor for dependency injection, Spring automatically provides an instance of ChannelRepository
    public ChannelService(ChannelRepository channelRepo) {
        this.channelRepo = channelRepo;
    }

    //Method to create and save a new channel in database, returns saved channel
    public Channel createChannel(Channel channel){
        return channelRepo.save(channel);
    }

    //method to retrieve all channels from database, return list of all channels
    public List<Channel> findAllChannels(){
        return channelRepo.findAll();
    }

    //method to find channel by ID, return if found
    public Optional<Channel> findChannelById(Long id){
        return channelRepo.findById(id);
    }

    //method to delete a channel by its ID, delete from database if found
    public void deleteChannelById(Long id){
        channelRepo.deleteById(id);
    }

    //method to update an existing channel in database, accepts new channel object with updated details
    //if channel with this ID exists - update name and save, if not return null
    public Channel updateChannel(Channel newChannel){
        return channelRepo.findById(newChannel.getId()).map(user -> {
            user.setName((newChannel.getName()));
            return channelRepo.save(user);
        }).orElse(null);
    }

}