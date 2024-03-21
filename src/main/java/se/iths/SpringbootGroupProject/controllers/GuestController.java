package se.iths.SpringbootGroupProject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.SpringbootGroupProject.entities.Message;
import se.iths.SpringbootGroupProject.entities.User;
import se.iths.SpringbootGroupProject.repositories.MessageRepository;
import se.iths.SpringbootGroupProject.repositories.UserRepository;

import java.util.List;

@RestController
public class GuestController {
    final UserRepository userRepository;
    final MessageRepository messageRepository;

    public GuestController(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @GetMapping("/users")
    List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @PostMapping("/users")
    ResponseEntity<Void> addUser(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{id}")
    User findOne(@PathVariable Long id){
        return userRepository.findById(id).get();
    }

    @GetMapping("/messages")
    List<Message> findAllPublicMessages() {
        return messageRepository.findAllByPrivateMessageIsFalse();
    }

    @GetMapping("/messages/all")
    List<Message> findAllMessages() {
        return (List<Message>) messageRepository.findAll();
    }

    @GetMapping("/messages/{userId}")
    List<Message> specificUserPublicMessage(@PathVariable Long userId) {
        var user = userRepository.findById(userId);
        if (user.isPresent()) {
            User user1 = user.get();
            return user1.getMessageList().stream().filter(message -> !message.isPrivateMessage()).toList();
        }
        return List.of();
    }

    @PostMapping("/messages/{userId}")
    ResponseEntity<Void> createMessage(@RequestBody Message message, @PathVariable Long userId) {
        messageRepository.save(message);
        var user = userRepository.findById(userId);
        if (user.isPresent()) {
            User user2 = user.get();
            user2.addMessage(message);
            userRepository.save(user2);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/messages/body/{messageId}")
    ResponseEntity<Void> updateBody(@RequestBody String message, @PathVariable Long messageId){
        messageRepository.editMessage(message,messageId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/messages/title/{messageId}")
    ResponseEntity<Void> updateTitle(@RequestBody String title, @PathVariable Long messageId){
        messageRepository.editMessage(title,messageId);
        return ResponseEntity.ok().build();
    }

}
