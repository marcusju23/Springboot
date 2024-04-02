package se.iths.springbootgroupproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.iths.springbootgroupproject.dto.MessageAndUsername;
import se.iths.springbootgroupproject.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class GuestController {

    private final MessageService messageService;

    public GuestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Gets all the public messages including authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found all public messages",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MessageAndUsername.class)))}
            ),
            @ApiResponse(responseCode = "204", description = "No messages found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Object.class)))})
    })
    @GetMapping("messages")
    List<MessageAndUsername> all() {
        var messages = messageService.findAllByPrivateMessageIsFalse();
        if (messages.isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        return messages;
    }

    @Operation(summary = "Gets all public messages from specific author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found all public messages",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MessageAndUsername.class)))}
            ),
            @ApiResponse(responseCode = "204", description = "No messages found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Object.class)))})
    })
    @GetMapping("messages/users/{id}")
    List<MessageAndUsername> allPublicUserMessages(@PathVariable Long id) {
        var messages = messageService.findAllByUserIdAndPrivateMessageIsFalse(id);
        if (messages.isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        return messages;
    }

}