package se.iths.springbootgroupproject.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import se.iths.springbootgroupproject.entities.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends ListCrudRepository<Message, Long> {

    List<Message> findAllByPrivateMessageIsFalse();

    Optional<Message> findByTitle(String title);

    @Query("""
            update Message m set m.privateMessage = ?1 where m.id = ?2
            """)
    @Modifying
    @Transactional
    String setMessagePrivacy(boolean isPrivate, Long id);

    @Query("""
            update Message m set m.messageBody = ?1 where m.id = ?2
            """)
    @Modifying
    @Transactional
    void editMessage(String updatedBody, Long id);

    @Query("""
            update Message m set m.title = ?1 where m.id = ?2
            """)
    @Modifying
    @Transactional
    String editTitle(String updatedTitle, Long id);


}
