package se.iths.springbootgroupproject.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.springbootgroupproject.dto.MessageAndUsername;
import se.iths.springbootgroupproject.services.MessageService;

import java.util.List;

@Controller
@RequestMapping("/web")
public class WebController {

    private final MessageService messageService;

    public WebController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/welcome")
    public String guestPage(@RequestParam(value = "page", defaultValue = "0") String page, Model model, HttpServletRequest httpServletRequest){
        int p = Integer.parseInt(page);
        if (p < 0) p = 0;
        List<MessageAndUsername> publicMessages = messageService.findAllByPrivateMessageIsFalse(PageRequest.of(p,10));
        int allPublicMessageCount = messageService.findAllByPrivateMessageIsFalse().size();
        model.addAttribute("messages", publicMessages);
        model.addAttribute("httpServletRequest", httpServletRequest);
        model.addAttribute("currentPage",p);
        model.addAttribute("totalPublicMessages", allPublicMessageCount);
        return "welcome";
    }
    @GetMapping("/messages")
    public String messagePage(@RequestParam(value = "page", defaultValue = "0") String page, Model model, HttpServletRequest httpServletRequest){
        int p = Integer.parseInt(page);
        if (p < 0) p = 0;
        List<MessageAndUsername> messages = messageService.findAllMessages(PageRequest.of(p,10));
        int allMessageCount = messageService.findAllMessages().size();
        model.addAttribute("messages",messages);
        model.addAttribute("httpServletRequest",httpServletRequest);
        model.addAttribute("currentPage",p);
        model.addAttribute("totalPublicMessages",allMessageCount);
        return "messages";
    }

}
