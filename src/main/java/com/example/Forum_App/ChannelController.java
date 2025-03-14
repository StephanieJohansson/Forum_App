package com.example.Forum_App;

//importing necessary classes for transaction management, HTTP response and logging
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

//annotation that marks this class as a controller where every method returns a domain object
@RestController
//annotation that specifies the base URL for all endpoints in this controller
@RequestMapping("/Channels")
public class ChannelController {

    //dependency injection of ChannelService and ChannelRepository
    private final ChannelService channelService;
    private final ChannelRepository channelRepository;

    // Explicit constructor, constructor for dependency injection.
    public ChannelController(ChannelService channelService, ChannelRepository channelRepository) {
        this.channelService = channelService;
        this.channelRepository = channelRepository;
    }

    //GET endpoint to retrieve all channels which has messages, return a list of Channel objects
    @GetMapping
    public List<Channel> getAllChannels() {
        return channelRepository.findAllWithMessages();
    }

    //GET endpoint to retrieve a specific channel by its ID, returns a ResponseEntity with the channel if found or else give error status
    @GetMapping("/{id}")
    public ResponseEntity<Channel> getChannel(@PathVariable Long id) {
        Logger logger = Logger.getLogger(ChannelController.class.getName());
        return channelRepository.findById(id)
                .map(channel -> {
                    logger.log(Level.INFO, "Channel: " + channel.getName()); // Log channel
                    logger.log(Level.INFO, "Messages: " + channel.getMessages()); // Log messages
                    return ResponseEntity.ok(channel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    //POST endpoint to create a new channel, accepting it in request body and return the created channel
    @PostMapping
    public ResponseEntity<Channel> createChannel(@RequestBody Channel channel) {
        Channel result = channelService.createChannel(channel);
        return ResponseEntity.ok(result);
    }

    //GET endpoint to retrieve all messages for a specific channel by its ID, returns if found or else error message
    @GetMapping("/{id}/messages")
    public ResponseEntity<List<Message>> getMessagesForChannel(@PathVariable Long id) {
        return channelRepository.findById(id)
                .map(channel -> ResponseEntity.ok(channel.getMessages()))
                .orElse(ResponseEntity.notFound().build());
    }

    //PUT endpoint to add a message to a specific channel by its ID, returns the updated channel if successful or else error
    @PutMapping("/{id}")
    //annotation to ensure that the operation if performed within a transaction
    @Transactional
    public ResponseEntity<Channel> addMessage(@PathVariable Long id, @RequestBody String messageContent) {
        Logger logger = Logger.getLogger(ChannelController.class.getName());
        return channelRepository.findById(id)
                .map(channel -> {
                    //logging channel name before adding message
                    logger.info("Adding message to channel: " + channel.getName());
                    //create a new message associated with the channel
                    Message newMessage = new Message();
                    newMessage.setContent(messageContent);
                    newMessage.setChannel(channel);
                    channel.addMessage(newMessage);
                    //save updated channel to database
                    channelRepository.save(channel);
                    //logging saved message content
                    logger.info("Message saved: " + newMessage.getContent());
                    return ResponseEntity.ok(channel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    //DELETE endpoint to delete a channel by its ID, return No Content status if successful or error if not found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Long id) {
        if (!channelRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        channelRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}