package se.iths.springbootgroupproject.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.springbootgroupproject.dto.PublicMessageAndUsername;
import se.iths.springbootgroupproject.entities.User;
import se.iths.springbootgroupproject.repositories.UserRepository;
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
    private final UserRepository userRepository;

    public WebController(MessageService messageService, UserService userService,UserRepository userRepository) {
        this.messageService = messageService;
        this.userService = userService;
        this.userRepository = userRepository;
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

    @GetMapping("/hello")
    public String userProfile (Model model, @AuthenticationPrincipal OAuth2User principal) {
        /*
            This is the endpoint users are pointed to after oauth2 login.
            The path should probably change name to /users/profiles and access the oauth2User or /users/profiles/{uniqueValue} and fetch
            uniqueValue from userService.
         */

        System.out.println("User LoginName " + principal.getAttribute("login"));

        User user = userRepository.findByUserName(principal.getAttribute("login"));
        System.out.println("USER FIRSTNAME " + user.getFirstName());

        String userName = principal.getAttribute("login");
        String profilePic = principal.getAttribute("avatar_url");
        String name = principal.getAttribute("name");

        System.out.println(principal.getAttribute("avatar_url").toString());
        model.addAttribute("name" , name);
        model.addAttribute("userName", userName);
        model.addAttribute("profilepic",profilePic);
        model.addAttribute("all", principal);
        return "hello";
    }
}
