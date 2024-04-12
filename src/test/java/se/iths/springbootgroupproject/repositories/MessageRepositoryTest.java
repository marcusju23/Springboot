package se.iths.springbootgroupproject.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import se.iths.springbootgroupproject.entities.Message;
import se.iths.springbootgroupproject.entities.User;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.datasource.url=jdbc:tc:mysql:8.3.0:///mydatabase"})
class MessageRepositoryTest {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    TestEntityManager entityManager;

    @Test
    @DisplayName("Saving a message")
    void savingAMessageReturn() {
        var message = new Message();

        Message insertedMessage = messageRepository.save(message);

        assertThat(entityManager.find(Message.class, insertedMessage.getId())).isEqualTo(message);
    }

    @Test
    @DisplayName("Updating a message")
    void updatingAUser() {
        var message = new Message();
        message.setTitle("TEST-TITLE");
        entityManager.persist(message);
        String newTitle = "UPDATED TEST-TITLE";

        message.setTitle(newTitle);
        messageRepository.save(message);

        assertThat(entityManager.find(Message.class, message.getId()).getTitle()).isEqualTo(newTitle);
    }

    @Test
    @DisplayName("Finding a message by id returns message with that id")
    void findingAMessageByIdReturnsMessageWithThatId() {
        var message = new Message();
        entityManager.persist(message);

        var retrievedMessage = messageRepository.findById(message.getId());

        assertThat(retrievedMessage).contains(message);
    }

    @Test
    @DisplayName("Find all returns a list with all messages")
    void findAllReturnsAListWithAllMessages() {
        var message = new Message();
        var message2 = new Message();
        entityManager.persist(message);
        entityManager.persist(message2);

        var result = messageRepository.findAll();

        assertThat(result).contains(message, message2);
    }

    @Test
    @DisplayName("Deleting a message")
    void deletingAMessage() {
        var message = new Message();
        entityManager.persist(message);

        messageRepository.delete(message);

        assertThat(entityManager.find(Message.class, message.getId())).isNull();
    }

    @Test
    @DisplayName("findAllByPrivateMessageIsFalse returns a list with MessageAndUsername dto where messages are public")
    void findAllByPrivateMessageIsFalseReturnsAListWithMessageAndUsernameDto() {
        var message = new Message();
        message.setPrivateMessage(false);
        var message2 = new Message();
        message2.setPrivateMessage(true);
        var message3 = new Message();
        message3.setPrivateMessage(false);
        entityManager.persist(message);
        entityManager.persist(message2);
        entityManager.persist(message3);

        var result = messageRepository.findAllByPrivateMessageIsFalse();

        assertThat(result).hasSize(2);
        assertThat(result.getFirst().id()).isEqualTo(message.getId());
        assertThat(result.getLast().id()).isEqualTo(message3.getId());
    }

    @Test
    @DisplayName("findAllByPrivateMessageIsFalse with Pageable returns list with MessageAndUsername dto where messages are public")
    void findAllByPrivateMessageIsFalseWithPageableReturnsListWithMessageAndUsernameDto() {
        var message = new Message();
        message.setPrivateMessage(false);
        var message2 = new Message();
        message2.setPrivateMessage(true);
        var message3 = new Message();
        message3.setPrivateMessage(false);
        entityManager.persist(message);
        entityManager.persist(message2);
        entityManager.persist(message3);
        Pageable pageable = PageRequest.of(0, 1);

        var result = messageRepository.findAllByPrivateMessageIsFalse(pageable);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().id()).isEqualTo(message.getId());
    }

    @Test
    @DisplayName("findAllByUser returns list with MessageAndUsername dto")
    void findAllByUserReturnsListWithMessageAndUsernameDto() {
        User user = new User();
        User user2 = new User();
        var message = new Message();
        message.setUser(user);
        var message2 = new Message();
        message2.setUser(user2);
        var message3 = new Message();
        message3.setUser(user);
        entityManager.persist(message);
        entityManager.persist(message2);
        entityManager.persist(message3);
        entityManager.persist(user);
        entityManager.persist(user2);
        var result = messageRepository.findAllByUser(user);

        assertThat(result).hasSize(2);
        assertThat(result.getFirst().id()).isEqualTo(message.getId());
        assertThat(result.getLast().id()).isEqualTo(message3.getId());
    }

    @Test
    @DisplayName("findAllByUser with Pageable returns list with MessageAndUsername dto")
    void findAllByUserWithPageableReturnsListWithMessageAndUsernameDto() {
        User user = new User();
        User user2 = new User();
        var message = new Message();
        message.setUser(user);
        var message2 = new Message();
        message2.setUser(user2);
        var message3 = new Message();
        message3.setUser(user);
        entityManager.persist(message);
        entityManager.persist(message2);
        entityManager.persist(message3);
        entityManager.persist(user);
        entityManager.persist(user2);
        Pageable pageable = PageRequest.of(0, 1);

        var result = messageRepository.findAllByUser(user, pageable);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().id()).isEqualTo(message.getId());
    }

    @Test
    @DisplayName("Finding a message by title returns message with that id")
    void findingAMessageByTitleReturnsMessageWithThatId() {
        var message = new Message();
        message.setTitle("TEST-TITLE");
        entityManager.persist(message);

        var retrievedMessage = messageRepository.findByTitle("TEST-TITLE");

        assertThat(retrievedMessage).contains(message);
    }

    @Test
    @DisplayName("findAllByUserIdAndPrivateMessageIsFalse returns list with MessageAndUsername dto")
    void findAllByUserIdAndPrivateMessageIsFalseReturnsListWithMessageAndUsernameDto() {
        User user = new User();
        User user2 = new User();
        var message = new Message();
        message.setUser(user);
        message.setPrivateMessage(false);
        var message2 = new Message();
        message2.setUser(user2);
        message2.setPrivateMessage(true);
        var message3 = new Message();
        message3.setUser(user);
        message3.setPrivateMessage(false);
        entityManager.persist(message);
        entityManager.persist(message2);
        entityManager.persist(message3);
        entityManager.persist(user);
        entityManager.persist(user2);

        var result = messageRepository.findAllByUserIdAndPrivateMessageIsFalse(user.getId());

        assertThat(result).hasSize(2);
        assertThat(result.getFirst().id()).isEqualTo(message.getId());
        assertThat(result.getLast().id()).isEqualTo(message3.getId());
    }

}