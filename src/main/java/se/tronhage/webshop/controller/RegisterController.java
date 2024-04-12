package se.tronhage.webshop.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.exceptions.UserAlreadyExistsException;
import se.tronhage.webshop.repository.UserRepo;
import se.tronhage.webshop.services.EmailService;
import se.tronhage.webshop.services.UserService;

@Controller
public class RegisterController {

    private final UserService userService;
    private final EmailService emailService;

    public RegisterController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/register")
    public String showRegisterUserForm(Model m){
        m.addAttribute("customer", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUserForm(Model m, @Valid @ModelAttribute("customer") User user,
                                   BindingResult result) {
                                   if (result.hasErrors()) {
                                       return "register";
                                   }
        try {
            userService.registerNewUser(user.getFirstName(), user.getLastName(), user.getEmail(),
                    user.getAddress(), user.getUsername(), user.getPassword());

            // Skicka bekräftelsemail
            emailService.sendSimpleMessage(user.getEmail(), "Registration Confirmation",
                    emailService.regMessage(user.getUsername()));

            // Om användaren registreras korrekt, skicka användaren till inloggningssidan
            m.addAttribute("registrationSuccess", true);

        } catch (UserAlreadyExistsException e) {
            m.addAttribute("customer", new User()); // Återställer användarobjektet
            m.addAttribute("errorMessage", "Username already in use.");
            return "register";
        } catch (Exception e) {
            m.addAttribute("customer", new User()); // Återställer användarobjektet
            m.addAttribute("errorMessage", "Error during registration.");
            return "register";
        }
        return "redirect:/login";
    }
}
