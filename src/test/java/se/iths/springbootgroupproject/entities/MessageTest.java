package se.iths.springbootgroupproject.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageTest {

    private Message message = new Message();
    private User user = new User();
    private LocalDate date = LocalDate.now();

    @BeforeEach
    void setUp() {
        message = new Message();
        user = new User();
        date = LocalDate.now();
    }

    @Test
    @DisplayName("Setting and getting user")
    void setAndGetUser() {
        message.setUser(user);

        assertEquals(user, message.getUser());
    }

    @Test
    @DisplayName("Setting and getting id")
    void setAndGetId() {
        Long id = 1L;
        message.setId(id);

        assertEquals(id, message.getId());
    }

    @Test
    @DisplayName("Setting and getting date")
    void setAndGetDate() {
        message.setDate(date);

        assertEquals(date, message.getDate());
    }

    @Test
    @DisplayName("Setting and getting title")
    void setAndGetTitle() {
        String title = "Title";
        message.setTitle(title);

        assertEquals(title, message.getTitle());
    }

    @Test
    @DisplayName("Setting and getting message body")
    void setAndGetMessageBody() {
        String messageBody = "Hello";
        message.setMessageBody(messageBody);

        assertEquals(messageBody, message.getMessageBody());
    }

    @Test
    @DisplayName("Setting and getting private message flag")
    void setAndGetPrivateMessage() {
        boolean privateMessage = true;
        message.setPrivateMessage(privateMessage);

        assertEquals(privateMessage, message.isPrivateMessage());
    }

    @Test
    @DisplayName("Setting and getting last changed date")
    void setAndGetLastChanged() {
        message.setLastChanged(date);

        assertEquals(date, message.getLastChanged());
    }

}