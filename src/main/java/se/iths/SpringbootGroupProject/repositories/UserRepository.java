package se.iths.SpringbootGroupProject.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import se.iths.SpringbootGroupProject.entities.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
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
