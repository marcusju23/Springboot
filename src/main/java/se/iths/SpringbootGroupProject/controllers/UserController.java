package se.iths.SpringbootGroupProject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.SpringbootGroupProject.entities.User;
import se.iths.SpringbootGroupProject.repositories.MessageRepository;
import se.iths.SpringbootGroupProject.repositories.UserRepository;

import java.util.List;

@RestController
public class UserController {
    final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
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

}

