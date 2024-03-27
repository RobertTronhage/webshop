package se.tronhage.webshop.services;

import org.springframework.stereotype.Service;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.enums.Role;
import se.tronhage.webshop.repository.UserRepo;
import se.tronhage.webshop.exceptions.UserAlreadyExistsException;
import se.tronhage.webshop.exceptions.UserNotFoundException;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User registerNewUser(String username, String name, String email, String password, String adress) {
        if(userRepo.existsByUsername(username)) {
            throw new UserAlreadyExistsException("Användarnamnet är redan taget.");
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setAdress(adress);
        newUser.setName(name);
        newUser.setPassword(password);
        newUser.setRole(Role.user);
        //newUser.setShoppingBasket(new ShoppingBasket()); // ny tom varukorg för användaren
        return userRepo.save(newUser);
    }


    public User updateUserDetails(Long userId, String email, String newPassword) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Användaren hittades inte."));
        user.setEmail(email);
        return userRepo.save(user);
    }

    public boolean authUser(String username, String password){
        Optional <User> user = userRepo.findByUsername(username);

        if (user.isPresent()){
            String dbPw = user.get().getPassword();

            return password.equals(dbPw);
        }else {
            return false;
        }
    }


}
