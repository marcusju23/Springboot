package se.iths.springbootgroupproject.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import se.iths.springbootgroupproject.dto.MessageAndUsername;
import se.iths.springbootgroupproject.entities.Message;
import se.iths.springbootgroupproject.entities.User;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends ListCrudRepository<Message, Long>, ListPagingAndSortingRepository<Message, Long> {
    List<MessageAndUsername> findAllByPrivateMessageIsFalse();

    List<MessageAndUsername> findAllByPrivateMessageIsFalse(Pageable pageable);

    List<MessageAndUsername> findAllByUser(User user);

    List<MessageAndUsername> findAllByUser(User user, Pageable pageable);

    Optional<Message> findByTitle(String title);

    @EntityGraph(attributePaths = "user.userName")
    List<MessageAndUsername> findAllByUserIdAndPrivateMessageIsFalse(Long id);

}
