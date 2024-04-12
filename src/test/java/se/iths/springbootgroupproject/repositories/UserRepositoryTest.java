package se.iths.springbootgroupproject.repositories;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import se.iths.springbootgroupproject.entities.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.datasource.url=jdbc:tc:mysql:8.3.0:///mydatabase"})
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TestEntityManager entityManager;

    @Test
    @DisplayName("Saving a user")
    void savingAUser() {
        var user = initUser("TEST-USER");

        User insertedUser = userRepository.save(user);

        assertThat(entityManager.find(User.class, insertedUser.getId())).isEqualTo(user);
    }

    @Test
    @DisplayName("Updating a user")
    void updatingAUser() {
        var user = initUser("TEST-USER");

        entityManager.persist(user);
        String newName = "UPDATED TEST-USER";

        user.setUserName(newName);
        userRepository.save(user);

        assertThat(entityManager.find(User.class, user.getId()).getUserName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("Finding a user by id returns user with that id")
    void findingAUserByIdReturnsUserWithThatId() {
        var user = initUser("TEST-USER");
        entityManager.persist(user);

        Optional<User> retrievedUser = userRepository.findById(user.getId());

        assertThat(retrievedUser).contains(user);
    }

    @Test
    @DisplayName("Find all returns a list with all users")
    void findAllReturnsAListWithAllUsers() {
        var user = initUser("TEST-USER");
        var user2 = initUser("TEST-USER 2");
        entityManager.persist(user);
        entityManager.persist(user2);

        List<User> result = userRepository.findAll();

        assertThat(result).contains(user, user2);

    }

    @Test
    @DisplayName("Deleting a user")
    void deletingAUser() {
        var user = initUser("TEST-USER");
        entityManager.persist(user);

        userRepository.delete(user);
        assertThat(entityManager.find(User.class, user.getId())).isNull();
    }

    @Test
    @DisplayName("findByFirstName returns list of users with same firstName")
    void findByFirstNameReturnsListOfUsersWithSameFirstName() {
        var user = initUser("TEST-USER 1");
        var user2 = initUser("TEST-USER 2");
        var user3 = initUser("TEST-USER 3");
        entityManager.persist(user);
        entityManager.persist(user2);
        entityManager.persist(user3);

        var result = userRepository.findByFirstName("JEFF");

        assertThat(result).contains(user, user2, user3);
    }

    @Test
    @DisplayName("findByLastName returns list of users with same lastName")
    void findByLastNameReturnsListOfUsersWithSameLastName() {
        var user = initUser("TEST-USER 1");
        var user2 = initUser("TEST-USER 2");
        var user3 = initUser("TEST-USER 3");
        entityManager.persist(user);
        entityManager.persist(user2);
        entityManager.persist(user3);

        var result = userRepository.findByLastName(("TESTSSON"));

        assertThat(result).contains(user, user2, user3);
    }

    @Test
    @DisplayName("findByUserName returns user with that username")
    void findByUserNameReturnsUserWithThatUsername() {
        var user = initUser("TEST-USER");
        entityManager.persist(user);

        Optional<User> retrievedUser = userRepository.findByUserName(user.getUserName());

        assertThat(retrievedUser).contains(user);
    }

    @Test
    @DisplayName("findByGitHubId returns user with that githubId")
    void findByGitHubIdReturnsUserWithThatGithubId() {
        var user = initUser("TEST-USER");
        entityManager.persist(user);

        Optional<User> retrievedUser = userRepository.findByGithubId(user.getGithubId());

        assertThat(retrievedUser).contains(user);
    }

    @Test
    @DisplayName("findByEmail returns user with that email")
    void findByEmailReturnsUserWithThatEmail() {
        var user = initUser("TEST-USER");
        entityManager.persist(user);

        Optional<User> retrievedUser = userRepository.findByEmail(user.getEmail());

        assertThat(retrievedUser).contains(user);
    }

    @Test
    @DisplayName("updateEmail should update email")
    void updateEmailShouldUpdateEmail() {
        var user = initUser("TEST-USER");
        entityManager.persist(user);
        String newEmail = "EMAIL@FOR.TEST";

        user.setEmail(newEmail);
        userRepository.save(user);

        assertThat(entityManager.find(User.class, user.getId()).getEmail()).isEqualTo(newEmail);
    }

    @NotNull
    private static User initUser(String username) {
        User user = new User();
        user.setUserName(username);
        user.setEmail("USER@TEST.TESTING");
        user.setFirstName("JEFF");
        user.setLastName("TESTSSON");
        user.setGithubId(123456789);
        return user;
    }

}