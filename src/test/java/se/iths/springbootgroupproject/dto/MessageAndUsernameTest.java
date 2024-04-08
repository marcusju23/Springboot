package se.iths.springbootgroupproject.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageAndUsernameTest {

    @Test
    @DisplayName("Record")
    void record() {
        Long id = 1L;
        LocalDate date = LocalDate.of(1892, 1, 3);
        LocalDate lastChanged = LocalDate.of(2024, 4, 5);
        String title = "Epic Title";
        String messageBody = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor " +
                "in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint " +
                "occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        String userName = "Mithrandir";
        MessageAndUsername record = new MessageAndUsername(id, date, lastChanged, title, messageBody, userName);

        assertEquals(id, record.id());
        assertEquals(date, record.date());
        assertEquals(lastChanged, record.lastChanged());
        assertEquals(title, record.title());
        assertEquals(messageBody, record.messageBody());
        assertEquals(userName, record.userUserName());
    }

}