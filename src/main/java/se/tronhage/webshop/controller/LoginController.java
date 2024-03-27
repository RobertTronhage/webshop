package se.tronhage.webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.enums.Role;
import se.tronhage.webshop.repository.UserRepo;
import se.tronhage.webshop.services.UserService;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    @GetMapping("/login")
    public String displayLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(Model m,
                        @RequestParam String username,
                        @RequestParam String password
    ) {
        boolean auth = userService.authUser(username, password);

        if (auth) {
            Optional<User> userOptional = userRepo.findByUsername(username);
            if (userOptional.isPresent()) {

                User user = userOptional.get();

                if (user.getRole() == Role.admin) {
                    return "redirect:/admin.html";
                } else {
                    return "products";
                }
            } else {
                m.addAttribute("error", "user not found");
                return "login";
            }
        } else {
            m.addAttribute("error", "invalid username or password");
            return "login";
        }

    }
}