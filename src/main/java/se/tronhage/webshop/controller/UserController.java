package se.tronhage.webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.tronhage.webshop.entity.Order;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.repository.UserRepo;
import se.tronhage.webshop.services.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;
    private final UserRepo userRepo;

    @Autowired
    public UserController(UserService userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @GetMapping("/users")
    public String listUsers(@RequestParam(name = "type", required = false, defaultValue = "all") String type, Model m) {
        List<User> users = userRepo.findAll();

        switch (type) {
            case "admin" -> {
                users = userService.findAllAdmins();
            }
            case "user" -> {
                users = userService.findallRegularUsers();
            }
            case "all" -> users = userRepo.findAll();
        }
        m.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/users")
    public String listUsersPost(@RequestParam(name = "type", required = false, defaultValue = "all") String type) {

        return "redirect:/users?type=" + type;
    }

    @GetMapping("/edituser")
    public String editUser(@RequestParam("userId") Long userId, Model model) {
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
            return "edituser";
        } else {
            return "errorPage";
        }
    }

    @PostMapping("/edituser")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }
}
