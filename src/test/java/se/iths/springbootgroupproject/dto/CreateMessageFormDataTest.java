package se.iths.springbootgroupproject.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.iths.springbootgroupproject.entities.Message;
import se.iths.springbootgroupproject.entities.User;

import static org.junit.jupiter.api.Assertions.*;

class CreateMessageFormDataTest {

    private Validator validator;
    private User user = new User();

    @BeforeEach
    void setUp() {
        user = new User();
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

    }

    @Test
    @DisplayName("Validation constraints")
    void validateConstraints() {
        CreateMessageFormData formData = new CreateMessageFormData("", "", false);
        var violations = validator.validate(formData);

        assertEquals(4, violations.size());
    }

    @Test
    @DisplayName("Conversion to Message entity")
    void convertToMessageEntity() {
        CreateMessageFormData formData = new CreateMessageFormData("I don't know",
                "Something here", true);
        Message message = formData.toEntity(user);

        assertEquals(formData.getTitle(), message.getTitle());
        assertEquals(formData.getMessageBody(), message.getMessageBody());
        assertEquals(formData.isPrivateMessage(), message.isPrivateMessage());
        assertEquals(user, message.getUser());
    }

    @Test
    @DisplayName("Getters and setters")
    void getterAndSetter() {
        CreateMessageFormData formData = new CreateMessageFormData();
        formData.setTitle("Best title");
        formData.setMessageBody("Something something yes");
        formData.setPrivateMessage(true);

        assertEquals("Best title", formData.getTitle());
        assertEquals("Something something yes", formData.getMessageBody());
        assertTrue(formData.isPrivateMessage());
    }

    @Test
    @DisplayName("No arguments constructor")
    void noArgsConstructor() {
        CreateMessageFormData formData = new CreateMessageFormData();

        assertNotNull(formData);
    }

    @Test
    @DisplayName("All arguments constructor")
    void allArgsConstructor() {
        CreateMessageFormData formData = new CreateMessageFormData("Title",
                "Body", true);

        assertEquals("Title", formData.getTitle());
        assertEquals("Body", formData.getMessageBody());
        assertTrue(formData.isPrivateMessage());
    }

}