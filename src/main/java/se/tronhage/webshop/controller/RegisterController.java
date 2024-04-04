package se.tronhage.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.exceptions.UserAlreadyExistsException;
import se.tronhage.webshop.repository.UserRepo;
import se.tronhage.webshop.services.UserService;

@Controller
public class RegisterController {

    private final UserRepo userRepo;
    private final UserService userService;

    public RegisterController(UserRepo userRepo, UserService userService) {
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterUserForm(Model m){
        m.addAttribute("customer",new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUserForm(Model m,
                                   @RequestParam String firstName,
                                   @RequestParam String lastName,
                                   @RequestParam String email,
                                   @RequestParam String address,
                                   @RequestParam String username,
                                   @RequestParam String password) {
        try {
            userService.registerNewUser(firstName, lastName, email, address, username, password);
            // Om användaren registreras korrekt, skicka användaren till inloggningssidan
            m.addAttribute("registrationSuccess", true);

        } catch (UserAlreadyExistsException e) {
            m.addAttribute("customer", new User());
            m.addAttribute("errorMessage", "Username already in use.");
            return "register";
        } catch (Exception e) {
            m.addAttribute("customer", new User()); // Återställer användarobjektet
            m.addAttribute("errorMessage", "Error during registering.");
            return "register";
        }
        return "redirect:/login";
    }
}
