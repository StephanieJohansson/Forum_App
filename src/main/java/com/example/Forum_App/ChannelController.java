package com.example.Forum_App;

import jakarta.transaction.Transactional;
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

    //mapping GET-requests to this method
    @GetMapping
    public List<Channel> getAllChannels() {
        //returning the list of all channels from database
        return channelRepository.findAll();
    }

    //handles post-request to create a new channel
    @PostMapping
    public Channel createChannel(@RequestBody Channel channel) {
        //save-method returns the saved entity
        return channelRepository.save(channel);
    }

    //handles get-requests to retrieve a specific channel by ID
    //{ID}=path variable
    @GetMapping("/{id}")
    public ResponseEntity<Channel> getChannel(@PathVariable Long id) {
        //attempts to find channel by ID using repository. If it's found then return it, or else return 404 message
        return channelRepository.findById(id)
                .map(channel -> ResponseEntity.ok().body(channel))
                .orElse(ResponseEntity.notFound().build());
    }

    //handles put-requests to add message to specific channel. requestbody indicates that the payload of the
    //request will be a string (the message)
    @PutMapping("/{id}")
    @Transactional //keeping the hibernate session open through the whole method
    public ResponseEntity<Channel> addMessage(@PathVariable Long id, @RequestBody String message) {
        //attempts to find channel by ID using repository. If it's found add message to channel's list of messages,
        //update channel and save back to database, return with OK status. Or else return response 404
        return channelRepository.findById(id)
                .map(channel -> {
                    Message messages = new Message();
                    messages.setContent(message);
                    messages.setChannel(channel); // Koppla meddelandet till kanalen
                    channel.addMessage(messages); // Lägg till meddelandet i kanalen
                    channelRepository.save(channel); // Spara kanalen (och meddelandet)
                    return ResponseEntity.ok(channel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    //handles delete-requests to delete specific channel by ID
    @DeleteMapping("/{id}")
    //check if the channel exists in database. If not then return HTTP 404 response
    public ResponseEntity<Void> deleteChannel(@PathVariable Long id) {
        if (!channelRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        //If it exists then delete it from database, return 204 No Content response to indicate successful delete
        channelRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
