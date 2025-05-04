package se.iths.springbootgroupproject.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import se.iths.springbootgroupproject.entities.User;
import se.iths.springbootgroupproject.services.UserService;

@Controller
public class RegisterController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Add an empty User object for binding
        return "register"; // This is the name of your registration form view
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register"; // If validation fails, return to the registration form
        }

        // Check if the username or email already exists
        if (userService.findByUserName(user.getUserName()).isPresent()) {
            model.addAttribute("usernameError", "Username already taken.");
            return "register";
        }

        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("emailError", "Email already registered.");
            return "register";
        }

        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user
        userService.save(user);

        return "redirect:/login"; // Redirect to the login page after successful registration
    }
}
