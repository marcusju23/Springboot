package se.iths.springbootgroupproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import se.iths.springbootgroupproject.entities.User;

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

    public EditUserFormData() {
    }
    
    public EditUserFormData(String userName, String firstName, String lastName, String email, String image) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
