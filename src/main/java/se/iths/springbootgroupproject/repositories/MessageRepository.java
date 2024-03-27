package se.iths.springbootgroupproject.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import se.iths.springbootgroupproject.dto.MessageAndUsername;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import se.iths.springbootgroupproject.entities.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends ListCrudRepository<Message, Long>, ListPagingAndSortingRepository<Message, Long> {

    List<MessageAndUsername> findAllByPrivateMessageIsFalse();
    List<MessageAndUsername> findAllByPrivateMessageIsFalse(Pageable pageable);
    Optional<Message> findByTitle(String title);

    @Query("""
            update Message m set m.privateMessage = ?1 where m.id = ?2
            """)
    @Modifying
    @Transactional
    void setMessagePrivacy(boolean isPrivate, Long id);

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
    void editTitle(String updatedTitle, Long id);


}
