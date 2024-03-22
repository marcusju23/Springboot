package se.iths.SpringbootGroupProject.controllers;

import org.springframework.web.bind.annotation.*;
import se.iths.SpringbootGroupProject.dto.PublicMessageAndUsername;
import se.iths.SpringbootGroupProject.repositories.MessageRepository;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class GuestController {

    private final MessageRepository messageRepository;

    public GuestController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("messages")
    List<PublicMessageAndUsername> all(){
            return messageRepository.findAllByPrivateMessageIsFalse()
                    .stream()
                    .map(message -> new PublicMessageAndUsername(
                            message.getDate(),
                            message.getTitle(),
                            message.getMessageBody(),
                            message.getUser().getUserName())).toList();
    }

}
