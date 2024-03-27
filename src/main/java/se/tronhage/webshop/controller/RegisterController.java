package se.tronhage.webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.repository.UserRepo;
import se.tronhage.webshop.services.UserService;

@Controller
public class RegisterController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String showRegisterUserForm(Model m){
        m.addAttribute("customer",new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUserForm(Model m,
                                   @RequestParam String name,
                                   @RequestParam String email,
                                   @RequestParam String password,
                                   @RequestParam String adress,
                                   @RequestParam String username
    ){
        String addUser = String.valueOf(userService.registerNewUser(username,name,email,password,adress));
        m.addAttribute("addUser",addUser);
        return "register";
    }
}
