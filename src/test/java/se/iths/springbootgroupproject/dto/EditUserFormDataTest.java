package se.iths.springbootgroupproject.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.iths.springbootgroupproject.entities.User;

import static org.junit.jupiter.api.Assertions.*;

class EditUserFormDataTest {

    private Validator validator;
    private EditUserFormData formData = new EditUserFormData();

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        formData = new EditUserFormData();
    }

    @Test
    @DisplayName("Validation constraints")
    void validateConstraints() {
        EditUserFormData formData = new EditUserFormData("", "", "",
                "invalid-email", "invalid-url");
        var violations = validator.validate(formData);

        assertEquals(5, violations.size());
    }

    @Test
    @DisplayName("No arguments constructor")
    void noArgsConstructor() {
        EditUserFormData formData = new EditUserFormData();

        assertNotNull(formData);
    }

    @Test
    @DisplayName("All arguments constructor")
    void allArgsConstructor() {
        EditUserFormData formData = new EditUserFormData("username", "firstname",
                "lastname", "test@testing.com", "https://example.com/image.jpg");

        assertEquals("username", formData.getUserName());
        assertEquals("firstname", formData.getFirstName());
        assertEquals("lastname", formData.getLastName());
        assertEquals("test@testing.com", formData.getEmail());
        assertEquals("https://example.com/image.jpg", formData.getImage());
    }

    @Test
    @DisplayName("Setters and getters")
    void settersAndGetters() {
        formData.setUserName("username");
        formData.setFirstName("firstname");
        formData.setLastName("lastname");
        formData.setEmail("testing@test.com");
        formData.setImage("https://example.com/image.jpg");

        assertEquals("username", formData.getUserName());
        assertEquals("firstname", formData.getFirstName());
        assertEquals("lastname", formData.getLastName());
        assertEquals("testing@test.com", formData.getEmail());
        assertEquals("https://example.com/image.jpg", formData.getImage());
    }

    @Test
    @DisplayName("To entity")
    void toEntity() {
        formData.setUserName("username");
        formData.setFirstName("firstname");
        formData.setLastName("lastname");
        formData.setEmail("test@testing.com");
        formData.setImage("https://example.com/image.jpg");
        User user = formData.toEntity();

        assertEquals("username", user.getUserName());
        assertEquals("firstname", user.getFirstName());
        assertEquals("lastname", user.getLastName());
        assertEquals("test@testing.com", user.getEmail());
        assertEquals("https://example.com/image.jpg", user.getImage());
    }

}