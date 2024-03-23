package se.iths.SpringbootGroupProject.controllers;

import org.springframework.web.bind.annotation.*;
import se.iths.SpringbootGroupProject.dto.PublicMessageAndUsername;
import se.iths.SpringbootGroupProject.repositories.MessageRepository;
import se.iths.SpringbootGroupProject.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class GuestController {

    private final MessageService messageService;

    public GuestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("messages")
    List<PublicMessageAndUsername> all(){
            return messageService.findAllByPrivateMessageIsFalse();
    }

}