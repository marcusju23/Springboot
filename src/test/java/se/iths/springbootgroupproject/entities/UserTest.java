package se.iths.springbootgroupproject.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserTest {

    private User user = new User();

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    @DisplayName("Setting and getting id")
    void setAndGetId() {
        Long id = 1L;
        user.setId(id);

        assertEquals(id, user.getId());
    }

    @Test
    @DisplayName("Setting and getting username")
    void setAndGetUserName() {
        String username = "testdude";
        user.setUserName(username);

        assertEquals(username, user.getUserName());
    }

    @Test
    @DisplayName("Setting and getting image")
    void setAndGetImage() {
        String image = "test.jpg";
        user.setImage(image);

        assertEquals(image, user.getImage());
    }

    @Test
    @DisplayName("Setting and getting first name")
    void setAndGetFirstName() {
        String firstName = "Test";
        user.setFirstName(firstName);

        assertEquals(firstName, user.getFirstName());
    }

    @Test
    @DisplayName("Setting and getting last name")
    void setAndGetLastName() {
        String lastName = "Dude";
        user.setLastName(lastName);

        assertEquals(lastName, user.getLastName());
    }

    @Test
    @DisplayName("Setting and getting email")
    void setAndGetEmail() {
        String email = "test.dude@test.com";
        user.setEmail(email);

        assertEquals(email, user.getEmail());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 39, 107, 2164, 15_846, 80_658, 1_000_117})
    @DisplayName("Setting and getting github id")
    void setAndGetGithubId(int githubId) {
        user.setGithubId(githubId);

        assertEquals(githubId, user.getGithubId());
    }

    @Test
    @DisplayName("Two users with same ID should be equal")
    void twoUserWithSameIdShouldBeEqual() {
        user.setId(1L);
        User user2 = new User();
        user2.setId(1L);
        assertEquals(user, user2);

        user2.setId(2L);
        assertNotEquals(user, user2);
    }

    @Test
    @DisplayName("Hash code remains consistent for the same object instance")
    void hashCodeConsistencyForSameObject() {
        assertEquals(user.hashCode(), user.hashCode());
    }

    /*
    *       Several FullName tests
    * */
    @ParameterizedTest
    @ValueSource(strings = {"The Dude", "Dude", "TheDude"})
    @DisplayName("Setting full name")
    void setFullName(String fullName) {
        user.setFullName(fullName);
        String[] parts = fullName.trim().split("\\s+");
        String expectedFirstName = parts.length >= 1 ? parts[0] : "";
        String expectedLastName = parts.length >= 2 ? parts[1] : "";

        assertEquals(expectedFirstName, user.getFirstName());
        assertEquals(expectedLastName, user.getLastName());
    }

    @Test
    @DisplayName("Setting full name start with space")
    void setFullNameStartWithSpace() {
        String fullName = " TheDude";
        user.setFullName(fullName);

        assertNotEquals(" TheDude", user.getFirstName());
        assertEquals("TheDude", user.getFirstName());
        assertEquals("", user.getLastName());
    }

    @Test
    @DisplayName("Setting full name end with space")
    void setFullNameEndWithSpace() {
        String fullName = "TheDude ";
        user.setFullName(fullName);

        assertNotEquals("TheDude ", user.getFirstName());
        assertEquals("TheDude", user.getFirstName());
        assertEquals("", user.getLastName());
    }

}