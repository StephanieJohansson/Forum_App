package com.example.Forum_App;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

@RestController
@RequestMapping("/Channels")
public class ChannelController {

    private final ChannelService channelService;
    private final ChannelRepository channelRepository;

    // Explicit constructor
    public ChannelController(ChannelService channelService, ChannelRepository channelRepository) {
        this.channelService = channelService;
        this.channelRepository = channelRepository;
    }

    @GetMapping
    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

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

    @PostMapping
    public ResponseEntity<Channel> createChannel(@RequestBody Channel channel) {
        Channel result = channelService.createChannel(channel);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<List<Message>> getMessagesForChannel(@PathVariable Long id) {
        return channelRepository.findById(id)
                .map(channel -> ResponseEntity.ok(channel.getMessages()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Channel> addMessage(@PathVariable Long id, @RequestBody String messageContent) {
        Logger logger = Logger.getLogger(ChannelController.class.getName());
        return channelRepository.findById(id)
                .map(channel -> {
                    logger.info("Adding message to channel: " + channel.getName());
                    Message newMessage = new Message();
                    newMessage.setContent(messageContent);
                    newMessage.setChannel(channel);
                    channel.addMessage(newMessage);
                    channelRepository.save(channel);
                    logger.info("Message saved: " + newMessage.getContent());
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