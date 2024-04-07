package se.iths.springbootgroupproject.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.iths.springbootgroupproject.dto.MessageAndUsername;
import se.iths.springbootgroupproject.entities.Message;
import se.iths.springbootgroupproject.entities.User;
import se.iths.springbootgroupproject.repositories.MessageRepository;

@ContextConfiguration(classes = {MessageService.class})
@ExtendWith(SpringExtension.class)
class MessageServiceTest {

    @MockBean
    MessageRepository messageRepository;

    private MessageService messageService;
    private User user = new User();
    private User user2 = new User();
    private Message message = new Message();
    private Message message2 = new Message();

    @BeforeEach
    void setUp() {
        messageService = new MessageService(messageRepository);

        user.setEmail("test.ing@testing.com");
        user.setFirstName("Test");
        user.setGithubId(43247);
        user.setId(1L);
        user.setImage("test.jpg");
        user.setLastName("ing");
        user.setUserName("Testy");

        user2.setEmail("firstname.lastname@test.com");
        user2.setFirstName("Firstname");
        user2.setGithubId(934723);
        user2.setId(2L);
        user2.setImage("image.png");
        user2.setLastName("Lastname");
        user2.setUserName("TheUsername1");

        message.setDate(LocalDate.of(2023, 1, 1));
        message.setId(1L);
        message.setLastChanged(LocalDate.of(2024, 1, 1));
        message.setMessageBody("Hello");
        message.setPrivateMessage(true);
        message.setTitle("Greeting");
        message.setUser(user);

        message2.setDate(LocalDate.of(2016, 7, 5));
        message2.setId(2L);
        message2.setLastChanged(LocalDate.of(2020, 2, 3));
        message2.setMessageBody("Something something");
        message2.setPrivateMessage(false);
        message2.setTitle("Title");
        message2.setUser(user2);
    }

    @Test
    @DisplayName("Find all messages with private message set to false")
    void findAllMessagesWithPrivateMessageSetToFalse() {
        ArrayList<MessageAndUsername> messageAndUsernameList = new ArrayList<>();
        when(messageRepository.findAllByPrivateMessageIsFalse()).thenReturn(messageAndUsernameList);
        List<MessageAndUsername> actualFindAllByPrivateMessageIsFalseResult = messageService
                .findAllByPrivateMessageIsFalse();

        verify(messageRepository).findAllByPrivateMessageIsFalse();
        assertTrue(actualFindAllByPrivateMessageIsFalseResult.isEmpty());
        assertSame(messageAndUsernameList, actualFindAllByPrivateMessageIsFalseResult);
    }

    @Test
    @DisplayName("Find all messages with private message set to false and encountering EntityNotFoundException")
    void findAllMessagesWithPrivateMessageSetToFalseAndEncounteringEntityNotFoundException() {
        when(messageRepository.findAllByPrivateMessageIsFalse())
                .thenThrow(new EntityNotFoundException("An error occurred"));

        assertThrows(EntityNotFoundException.class, () -> messageService.findAllByPrivateMessageIsFalse());
        verify(messageRepository).findAllByPrivateMessageIsFalse();
    }

    @Test
    @DisplayName("Find all messages with private message set to false and using pagination")
    void findAllMessagesWithPrivateMessageSetToFalseAndUsingPagination() {
        ArrayList<MessageAndUsername> messageAndUsernameList = new ArrayList<>();
        when(messageRepository.findAllByPrivateMessageIsFalse(Mockito.<Pageable>any())).thenReturn(messageAndUsernameList);
        List<MessageAndUsername> actualFindAllByPrivateMessageIsFalseResult = messageService
                .findAllByPrivateMessageIsFalse(null);

        verify(messageRepository).findAllByPrivateMessageIsFalse(isNull());
        assertTrue(actualFindAllByPrivateMessageIsFalseResult.isEmpty());
        assertSame(messageAndUsernameList, actualFindAllByPrivateMessageIsFalseResult);
    }

    @Test
    @DisplayName("Find all messages with private message set to false and encountering EntityNotFoundException with pagination")
    void findAllMessagesWithPrivateMessageSetToFalseAndEncounteringEntityNotFoundExceptionWithPagination() {
        when(messageRepository.findAllByPrivateMessageIsFalse(Mockito.<Pageable>any()))
                .thenThrow(new EntityNotFoundException("An error occurred"));

        assertThrows(EntityNotFoundException.class, () -> messageService.findAllByPrivateMessageIsFalse(null));
        verify(messageRepository).findAllByPrivateMessageIsFalse(isNull());
    }

    @Test
    @DisplayName("Find all messages")
    void findAllMessages() {
        when(messageRepository.findAll()).thenReturn(new ArrayList<>());
        List<MessageAndUsername> actualFindAllMessagesResult = messageService.findAllMessages();

        verify(messageRepository).findAll();
        assertTrue(actualFindAllMessagesResult.isEmpty());
    }

    @Test
    @DisplayName("Find all messages with one message")
    void findAllMessagesWithOneMessage() {
        ArrayList<Message> messageList = new ArrayList<>();
        messageList.add(message);
        when(messageRepository.findAll()).thenReturn(messageList);
        List<MessageAndUsername> actualFindAllMessagesResult = messageService.findAllMessages();

        verify(messageRepository).findAll();
        assertEquals(1, actualFindAllMessagesResult.size());
    }

    @Test
    @DisplayName("Find all messages with two messages")
    void findAllMessagesWithTwoMessages() {
        ArrayList<Message> messageList = new ArrayList<>();
        messageList.add(message2);
        messageList.add(message);
        when(messageRepository.findAll()).thenReturn(messageList);
        List<MessageAndUsername> actualFindAllMessagesResult = messageService.findAllMessages();

        verify(messageRepository).findAll();
        assertEquals(2, actualFindAllMessagesResult.size());
    }

    @Test
    @DisplayName("Find all messages and encountering EntityNotFoundException")
    void findAllMessagesAndEncounteringEntityNotFoundException() {
        when(messageRepository.findAll()).thenThrow(new EntityNotFoundException("An error occurred"));

        assertThrows(EntityNotFoundException.class, () -> messageService.findAllMessages());
        verify(messageRepository).findAll();
    }

    @Test
    @DisplayName("Find all messages with pagination")
    void findAllMessagesWithPagination() {
        when(messageRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        List<MessageAndUsername> actualFindAllMessagesResult = messageService.findAllMessages(null);

        verify(messageRepository).findAll((Pageable) isNull());
        assertTrue(actualFindAllMessagesResult.isEmpty());
    }

    @Test
    @DisplayName("Find all messages with pagination and one message")
    void findAllMessagesWithPaginationAndOneMessage() {
        ArrayList<Message> content = new ArrayList<>();
        content.add(message);
        PageImpl<Message> pageImpl = new PageImpl<>(content);
        when(messageRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        List<MessageAndUsername> actualFindAllMessagesResult = messageService.findAllMessages(null);

        verify(messageRepository).findAll((Pageable) isNull());
        assertEquals(1, actualFindAllMessagesResult.size());
    }

    @Test
    @DisplayName("Find all messages with pagination and two messages")
    void findAllMessagesWithPaginationAndTwoMessages() {
        ArrayList<Message> content = new ArrayList<>();
        content.add(message2);
        content.add(message);
        PageImpl<Message> pageImpl = new PageImpl<>(content);
        when(messageRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        List<MessageAndUsername> actualFindAllMessagesResult = messageService.findAllMessages(null);

        verify(messageRepository).findAll((Pageable) isNull());
        assertEquals(2, actualFindAllMessagesResult.size());
    }

    @Test
    @DisplayName("Find all messages with pagination and encountering EntityNotFoundException")
    void findAllMessagesWithPaginationAndEncounteringEntityNotFoundException() {
        when(messageRepository.findAll(Mockito.<Pageable>any()))
                .thenThrow(new EntityNotFoundException("An error occurred"));

        assertThrows(EntityNotFoundException.class, () -> messageService.findAllMessages(null));
        verify(messageRepository).findAll((Pageable) isNull());
    }

    @Test
    @DisplayName("Find all messages by user")
    void findAllMessagesByUser() {
        ArrayList<MessageAndUsername> messageAndUsernameList = new ArrayList<>();
        when(messageRepository.findAllByUser(Mockito.<User>any())).thenReturn(messageAndUsernameList);
        List<MessageAndUsername> actualFindAllMessagesByUserResult = messageService.findAllMessagesByUser(user);

        verify(messageRepository).findAllByUser(isA(User.class));
        assertTrue(actualFindAllMessagesByUserResult.isEmpty());
        assertSame(messageAndUsernameList, actualFindAllMessagesByUserResult);
    }

    @Test
    @DisplayName("Find all messages by user and encountering EntityNotFoundException")
    void findAllMessagesByUserAndEncounteringEntityNotFoundException() {
        when(messageRepository.findAllByUser(Mockito.<User>any()))
                .thenThrow(new EntityNotFoundException("An error occurred"));

        assertThrows(EntityNotFoundException.class, () -> messageService.findAllMessagesByUser(user));
        verify(messageRepository).findAllByUser(isA(User.class));
    }

    @Test
    @DisplayName("Find all messages by user with pagination")
    void findAllMessagesByUserWithPagination() {
        ArrayList<MessageAndUsername> messageAndUsernameList = new ArrayList<>();
        when(messageRepository.findAllByUser(Mockito.<User>any(), Mockito.<Pageable>any()))
                .thenReturn(messageAndUsernameList);
        List<MessageAndUsername> actualFindAllMessagesByUserResult = messageService.findAllMessagesByUser(user, null);

        verify(messageRepository).findAllByUser(isA(User.class), isNull());
        assertTrue(actualFindAllMessagesByUserResult.isEmpty());
        assertSame(messageAndUsernameList, actualFindAllMessagesByUserResult);
    }

    @Test
    @DisplayName("Find all messages by user with pagination and encountering EntityNotFoundException")
    void findAllMessagesByUserWithPaginationAndEncounteringEntityNotFoundException() {
        when(messageRepository.findAllByUser(Mockito.<User>any(), Mockito.<Pageable>any()))
                .thenThrow(new EntityNotFoundException("An error occurred"));

        assertThrows(EntityNotFoundException.class, () -> messageService.findAllMessagesByUser(user, null));
        verify(messageRepository).findAllByUser(isA(User.class), isNull());
    }

    @Test
    @DisplayName("Save a message")
    void saveMessage() {
        messageService.save(message2);

        verify(messageRepository).save(isA(Message.class));
        assertEquals("2016-07-05", message2.getDate().toString());
        assertEquals("2020-02-03", message2.getLastChanged().toString());
        assertEquals("Title", message2.getTitle());
        assertEquals("Something something", message2.getMessageBody());
        assertEquals(2L, message2.getId().longValue());
        assertTrue(messageService.findAllByPrivateMessageIsFalse().isEmpty());
        assertFalse(message2.isPrivateMessage());
        assertEquals(user2, message2.getUser());
    }

    @Test
    @DisplayName("Save a message and encountering EntityNotFoundException")
    void saveMessageAndEncounteringEntityNotFoundException() {
        when(messageRepository.save(Mockito.<Message>any())).thenThrow(new EntityNotFoundException("An error occurred"));

        assertThrows(EntityNotFoundException.class, () -> messageService.save(message));
        verify(messageRepository).save(isA(Message.class));
    }

    @Test
    @DisplayName("Find a message by ID")
    void findMessageById() {
        Optional<Message> ofResult = Optional.of(message);
        when(messageRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Message actualFindByIdResult = messageService.findById(1L);

        verify(messageRepository).findById(isA(Long.class));
        assertSame(message, actualFindByIdResult);
    }

    @Test
    @DisplayName("Find a message by ID and encountering EntityNotFoundException with Empty Result")
    void findMessageByIdAndEncounteringEntityNotFoundExceptionWithEmptyResult() {
        Optional<Message> emptyResult = Optional.empty();
        when(messageRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        assertThrows(EntityNotFoundException.class, () -> messageService.findById(1L));
        verify(messageRepository).findById(isA(Long.class));
    }

    @Test
    @DisplayName("Find a message by ID and encountering EntityNotFoundException with Thrown Exception")
    void findMessageByIdAndEncounteringEntityNotFoundExceptionWithThrownException() {
        when(messageRepository.findById(Mockito.<Long>any())).thenThrow(new EntityNotFoundException("An error occurred"));

        assertThrows(EntityNotFoundException.class, () -> messageService.findById(1L));
        verify(messageRepository).findById(isA(Long.class));
    }

    @Test
    @DisplayName("Find all messages by user ID with private message set to false")
    void findAllMessagesByUserIdWithPrivateMessageSetToFalse() {
        ArrayList<MessageAndUsername> messageAndUsernameList = new ArrayList<>();
        when(messageRepository.findAllByUserIdAndPrivateMessageIsFalse(Mockito.<Long>any()))
                .thenReturn(messageAndUsernameList);
        List<MessageAndUsername> actualFindAllByUserIdAndPrivateMessageIsFalseResult = messageService
                .findAllByUserIdAndPrivateMessageIsFalse(1L);

        verify(messageRepository).findAllByUserIdAndPrivateMessageIsFalse(isA(Long.class));
        assertTrue(actualFindAllByUserIdAndPrivateMessageIsFalseResult.isEmpty());
        assertSame(messageAndUsernameList, actualFindAllByUserIdAndPrivateMessageIsFalseResult);
    }

    @Test
    @DisplayName("Find all messages by user ID with private message set to false and encountering EntityNotFoundException")
    void findAllMessagesByUserIdWithPrivateMessageSetToFalseAndEncounteringEntityNotFoundException() {
        when(messageRepository.findAllByUserIdAndPrivateMessageIsFalse(Mockito.<Long>any()))
                .thenThrow(new EntityNotFoundException("An error occurred"));

        assertThrows(EntityNotFoundException.class, () -> messageService.findAllByUserIdAndPrivateMessageIsFalse(1L));
        verify(messageRepository).findAllByUserIdAndPrivateMessageIsFalse(isA(Long.class));
    }

}