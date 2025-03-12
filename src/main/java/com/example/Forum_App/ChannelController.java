package com.example.Forum_App;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//setting the class as a REST-controller to deal with HTTP requests
@RestController
//defines base URL for every class in this controller - setting endpoint to /Channels
@RequestMapping("/Channels")
public class ChannelController {

    private final ChannelRepository channelRepository;

    //dependency injection (DI) to automatically handle the creation of an instance to this class
    @Autowired
    public ChannelController(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    //mapping GET-requests to this method, returning the list of all channels from database
    @GetMapping
    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    @PostMapping
    public Channel createChannel(@RequestBody Channel channel) {
        return channelRepository.save(channel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Channel> getChannel(@PathVariable Long id) {
        return channelRepository.findById(id)
                .map(channel -> ResponseEntity.ok().body(channel))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Channel> addMessage(@PathVariable Long id, @RequestBody String message) {
        return channelRepository.findById(id)
                .map(channel -> {
                    channel.getMessages().add(message);
                    channelRepository.save(channel);
                    return ResponseEntity.ok(channel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Long id) {
        if (!channelRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        channelRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
