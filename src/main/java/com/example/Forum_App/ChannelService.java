package com.example.Forum_App;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {

    ChannelRepository channelRepo;

    public ChannelService(ChannelRepository channelRepo) {
        this.channelRepo = channelRepo;
    }

    public Channel createChannel(Channel channel){
        return channelRepo.save(channel);
    }

    public List<Channel> findAllChannels(){
        return channelRepo.findAll();
    }

    public Optional<Channel> findChannelById(Long id){
        return channelRepo.findById(id);
    }

    public void deleteChannelById(Long id){
        channelRepo.deleteById(id);
    }

    public Channel updateChannel(Channel newChannel){
        return channelRepo.findById(newChannel.getId()).map(user -> {
            user.setName((newChannel.getName()));
            return channelRepo.save(user);
        }).orElse(null);
    }

}