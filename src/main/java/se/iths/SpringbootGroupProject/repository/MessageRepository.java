package se.iths.SpringbootGroupProject.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import se.iths.SpringbootGroupProject.entities.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findAllByPrivateMessageIsFalse();

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
