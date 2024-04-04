package se.iths.springbootgroupproject.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.iths.springbootgroupproject.dto.EditUserFormData;
import se.iths.springbootgroupproject.dto.MessageAndUsername;
import se.iths.springbootgroupproject.entities.User;
import se.iths.springbootgroupproject.services.MessageService;
import se.iths.springbootgroupproject.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/web")
public class WebController {

    private final MessageService messageService;
    private final UserService userService;

    public WebController(MessageService messageService,UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
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

    @GetMapping("/myprofile")
    public String userProfile (@RequestParam(value = "page", defaultValue = "0") String page, Model model, @AuthenticationPrincipal OAuth2User principal, HttpServletRequest httpServletRequest) {
        int p = Integer.parseInt(page);
        if (p < 0) p = 0;
        User user = userService.findByGitHubId(principal.getAttribute("id"));
        List<MessageAndUsername> messages = messageService.findAllMessagesByUser(user, PageRequest.of(p,10));
        int allMessageCount = messageService.findAllMessagesByUser(user).size();


        model.addAttribute("messages", messages);
        model.addAttribute("currentPage", p);
        model.addAttribute("totalMessages", allMessageCount);
        model.addAttribute("httpServletRequest", httpServletRequest);
        model.addAttribute("name" , user.getFirstName() + " " + user.getLastName());
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("profilepic",user.getImage());
        model.addAttribute("email", user.getEmail());

        return "userprofile";
    }

    @GetMapping("/myprofile/edit")
    public String editUserProfile(Model model, @AuthenticationPrincipal OAuth2User principal){
        User user = userService.findByGitHubId(principal.getAttribute("id"));
        model.addAttribute("formData", new EditUserFormData(user.getUserName(), user.getFirstName(), user.getLastName(), user.getEmail()));
        return "edituser";
    }

    @PostMapping("/myprofile/edit")
    public String editUserProfile(@Valid @ModelAttribute("formData") EditUserFormData userForm,
                    BindingResult bindingResult,
                    @AuthenticationPrincipal OAuth2User principal) {
        if(bindingResult.hasErrors()) {
            return "edituser";
        }
        User user = userService.findByGitHubId(principal.getAttribute("id"));
        user.setUserName(userForm.getUserName());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setEmail(userForm.getEmail());
        userService.save(user);
        return "redirect:/web/myprofile";
    }



}
