package se.iths.springbootgroupproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import se.iths.springbootgroupproject.entities.User;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditUserFormData {
    @NotNull
    @Size(min = 1, max = 100, message = "Username must be between 1 and 100 characters")
    private String userName;
    @NotNull
    @Size(min = 1, max = 100, message = "Firstname must be between 1 and 100 characters")
    private String firstName;
    @NotNull
    @Size(min = 1, max = 100, message = "Lastname must be between 1 and 100 characters")
    private String lastName;
    @Email(message = "PLEASEEE provide a valid email adress u ass")
    private String email;
    @URL
    private String image;

    public User toEntity() {
        User user = new User();
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setImage(image);
        return user;
    }

}
