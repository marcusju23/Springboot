package se.iths.springbootgroupproject.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.iths.springbootgroupproject.services.MessageService;
import se.iths.springbootgroupproject.services.UserService;

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
    public String getWelcomePage(Model model, HttpServletRequest httpServletRequest){
        var publicMessages = messageService.findAllByPrivateMessageIsFalse();
        model.addAttribute("messages",publicMessages);
        model.addAttribute("httpServletRequest", httpServletRequest);
        return "welcome";
    }

}
