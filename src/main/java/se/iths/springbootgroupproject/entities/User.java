package se.iths.springbootgroupproject.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Setter
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userName;
    @Column(name = "image")
    private String image;
    private String firstName;
    private String lastName;
    private String email;
    private Integer githubId;

    public void setFullName(String fullName) {
        String[] firstAndLastName = fullName.trim().split("\\s");
        firstName = firstAndLastName.length >= 1 ? firstAndLastName[0].trim() : "";
        lastName = firstAndLastName.length >= 2 ? firstAndLastName[1].trim() : "";
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        if (this instanceof HibernateProxy) {
            return ((HibernateProxy) this).getHibernateLazyInitializer().getIdentifier().hashCode();
        } else {
            return getId() != null ? getId().hashCode() : super.hashCode();
        }
    }
}
