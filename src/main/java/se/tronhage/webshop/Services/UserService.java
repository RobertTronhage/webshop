package se.tronhage.webshop.Services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.tronhage.webshop.Entity.User;
import se.tronhage.webshop.Enums.Role;
import se.tronhage.webshop.Repository.UserRepo;
import se.tronhage.webshop.exceptions.UserAlreadyExistsException;
import se.tronhage.webshop.exceptions.UserNotFoundException;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(String username, String email, String password, Role role) {
        if(userRepo.existsByUsername(username)) {
            throw new UserAlreadyExistsException("Användarnamnet är redan taget.");
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password)); // Hasha lösenordet
        newUser.setRole(role);
        //newUser.setShoppingBasket(new ShoppingBasket()); // ny tom varukorg för användaren
        return userRepo.save(newUser);
    }

    public User authenticateUser(String username, String password) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Användaren hittades inte."));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            throw new BadCredentialsException("Fel lösenord.");
        }
    }

    public User updateUserDetails(Long userId, String email, String newPassword) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Användaren hittades inte."));
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(newPassword)); // hasha det nya lösenordet
        return userRepo.save(user);
    }


}
