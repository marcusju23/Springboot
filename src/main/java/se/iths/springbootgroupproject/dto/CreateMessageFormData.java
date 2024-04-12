package se.iths.springbootgroupproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.iths.springbootgroupproject.entities.Message;
import se.iths.springbootgroupproject.entities.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMessageFormData {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 chars")
    private String title;

    @NotBlank
    @NotNull
    @Size(min = 1, max = 255, message = "Must contain between 1 and 255 chars")
    private String messageBody;

    @NotNull(message = "Set message to public or private")
    private boolean privateMessage;

    public Message toEntity(User user) {
        Message message = new Message();
        message.setTitle(title);
        message.setMessageBody(messageBody);
        message.setPrivateMessage(privateMessage);
        message.setUser(user);
        return message;
    }

}
