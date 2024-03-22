package se.iths.SpringbootGroupProject.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import se.iths.SpringbootGroupProject.entities.User;
import se.iths.SpringbootGroupProject.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable("firstName")
    public List<User> findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    @Cacheable("lastName")
    public List<User> findByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    @Cacheable("username")
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Cacheable("email")
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @CacheEvict(value = {"email", "username"}, allEntries = true)
    public void updateEmail(String email, Long id) {
        userRepository.updateEmail(email, id);
    }

}