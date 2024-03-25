package se.iths.springbootgroupproject.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import se.iths.springbootgroupproject.entities.User;

import java.util.List;

public interface UserRepository extends ListCrudRepository<User, Long> {
    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
    User findByUserName(String userName);
    User findByEmail(String email);

    @Query("""
            update User u set u.email = ?1 where u.id = ?2
            """)
    @Modifying
    @Transactional
    String updateEmail(String email, Long id);

}
