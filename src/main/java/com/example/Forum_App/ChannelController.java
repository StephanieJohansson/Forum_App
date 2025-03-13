package com.example.Forum_App;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/Channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;
    private final ChannelRepository channelRepository;

    @GetMapping
    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Channel> getChannel(@PathVariable Long id) {
        return channelRepository.findById(id)
                .map(channel -> {
                    log.info("Channel: {}", channel.getName());
                    log.info("Messages: {}", channel.getMessages());
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
    public ResponseEntity<Channel> addMessage(@PathVariable Long id, @RequestBody String message) {
        return channelRepository.findById(id)
                .map(channel -> {
                    Message newMessage = new Message();
                    newMessage.setContent(message);
                    newMessage.setChannel(channel);
                    channel.addMessage(newMessage);
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