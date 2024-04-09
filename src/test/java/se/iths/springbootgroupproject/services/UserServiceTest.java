package se.iths.springbootgroupproject.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.iths.springbootgroupproject.entities.User;
import se.iths.springbootgroupproject.repositories.UserRepository;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    private UserService userService;
    private User user = new User();

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
        user.setEmail("frodo.baggins@shire.com");
        user.setFirstName("Frodo");
        user.setGithubId(9);
        user.setId(1L);
        user.setImage("hobbit.jpg");
        user.setLastName("Baggins");
        user.setUserName("ringBearer");
    }

    @Test
    @DisplayName("Find by Firstname")
    void findByFirstName() {
        List<User> userList = new ArrayList<>();
        when(userRepository.findByFirstName(Mockito.<String>any())).thenReturn(userList);
        List<User> actualFindByFirstNameResult = userService.findByFirstName("Frodo");

        verify(userRepository).findByFirstName("Frodo");
        assertTrue(actualFindByFirstNameResult.isEmpty());
        assertSame(userList, actualFindByFirstNameResult);
    }

    @Test
    @DisplayName("Find by Lastname")
    void findByLastName() {
        List<User> userList = new ArrayList<>();
        when(userRepository.findByLastName(Mockito.<String>any())).thenReturn(userList);
        List<User> actualFindByLastNameResult = userService.findByLastName("Baggins");

        verify(userRepository).findByLastName("Baggins");
        assertTrue(actualFindByLastNameResult.isEmpty());
        assertSame(userList, actualFindByLastNameResult);
    }

    @Test
    @DisplayName("Find by Username")
    void findByUserName() {
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUserName(Mockito.<String>any())).thenReturn(ofResult);
        Optional<User> actualFindByUserNameResult = userService.findByUserName("ringBearer");

        verify(userRepository).findByUserName("ringBearer");
        assertSame(ofResult, actualFindByUserNameResult);
    }

    @Test
    @DisplayName("Find by Github ID")
    void findByGitHubId() {
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByGithubId(Mockito.<Integer>any())).thenReturn(ofResult);
        User actualFindByGitHubIdResult = userService.findByGitHubId(9);

        verify(userRepository).findByGithubId(9);
        assertSame(user, actualFindByGitHubIdResult);
    }

    @Test
    @DisplayName("Find by Email")
    void findByEmail() {
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(Optional.ofNullable(user));
        User actualFindByEmailResult = userService.findByEmail("frodo.baggins@shire.com");

        verify(userRepository).findByEmail("frodo.baggins@shire.com");
        assertSame(user, actualFindByEmailResult);
    }

    @Test
    @DisplayName("Update email")
    void updateEmail() {
        doNothing().when(userRepository).updateEmail(Mockito.<String>any(), Mockito.<Long>any());
        userService.updateEmail("frodo.baggins@shire.com", 1L);

        verify(userRepository).updateEmail("frodo.baggins@shire.com", 1L);
    }

    @Test
    @DisplayName("Saving user should populate fields correctly")
    void saveUser() {
        userService.save(user);

        verify(userRepository).save(user);
        assertEquals("Frodo", user.getFirstName());
        assertEquals("Baggins", user.getLastName());
        assertEquals("frodo.baggins@shire.com", user.getEmail());
        assertEquals("hobbit.jpg", user.getImage());
        assertEquals("ringBearer", user.getUserName());
        assertEquals(9, user.getGithubId().intValue());
        assertEquals(1L, user.getId().longValue());
    }

}