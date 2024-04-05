package se.iths.springbootgroupproject.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.iths.springbootgroupproject.entities.User;
import se.iths.springbootgroupproject.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Needs queries in UserRepository
    // @Cacheable("firstName")
    public List<User> findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    // Needs queries in UserRepository
    // @Cacheable("lastName")
    public List<User> findByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    @Cacheable("username")
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User findByGitHubId(Integer githubId){
        return userRepository.findByGithubId(githubId).orElse(null);
    }

    @Cacheable("email")
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @CacheEvict(value = {"email", "username"}, allEntries = true)
    public void updateEmail(String email, Long id) {
        userRepository.updateEmail(email, id);
    }

    @CacheEvict(value = {"email", "username"}, allEntries = true)
    public void save(User user) {
        userRepository.save(user);
    }

}