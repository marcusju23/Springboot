package se.iths.springbootgroupproject.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.springbootgroupproject.dto.PublicMessageAndUsername;
import se.iths.springbootgroupproject.services.MessageService;
import se.iths.springbootgroupproject.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/web")
public class WebController {
    /* ToDo:
     * Startsida: publika meddelanden, inloggningsruta, språkalternativ, välkomstmeddelande
     * Homepage efter inloggning: dina meddelanden, din profil: din bild, namn, efternamn,email, profilnamn, utloggningsruta, språkalternativ
     * Sida för att se alla meddelanden: Se alla meddelanden privata som publika där du ska kunna redigera ditt meddelande. utloggningsruta, språkalternativ
     *
     * OM TID FINNS-> Alla användare sida: kunna se alla användare och söka på användare
     */
    private final MessageService messageService;
    private final UserService userService;

    public WebController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/welcome")
    public String loadMorePublicMessages(@RequestParam(value = "page", defaultValue = "0") String page, Model model, HttpServletRequest httpServletRequest){
        int p = Integer.parseInt(page);
        if (p < 0)
            p = 0;
        List<PublicMessageAndUsername> publicMessages = messageService.findAllByPrivateMessageIsFalse(PageRequest.of(p,1));
        int allPublicMessageCount = messageService.findAllByPrivateMessageIsFalse().size();
        model.addAttribute("messages", publicMessages);
        model.addAttribute("httpServletRequest", httpServletRequest);
        model.addAttribute("currentPage",p);
        model.addAttribute("totalPublicMessages", allPublicMessageCount);
        return "welcome";
    }

}
