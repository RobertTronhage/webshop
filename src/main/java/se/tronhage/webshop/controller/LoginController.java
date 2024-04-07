package se.tronhage.webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.enums.Role;
import se.tronhage.webshop.repository.UserRepo;
import se.tronhage.webshop.services.UserService;

import java.util.Optional;

@Controller
public class LoginController {

    private final UserService userService;
    private final UserRepo userRepo;

    public LoginController(UserService userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @GetMapping("/login")
    public String displayLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        RedirectAttributes redirectAttributes) {
        Optional<Role> role = userService.authenticate(username, password);

        if (role.isPresent()) {
            if (role.get() == Role.ADMIN) {
                return "redirect:/admin";
            }
            return "redirect:/webshop";
        } else {
            redirectAttributes.addFlashAttribute("loginError",
                    "Invalid username or password");
            return "redirect:/login";
        }
    }

    @PostMapping("/logout")
    public String logout() {
        return "login";
    }
}