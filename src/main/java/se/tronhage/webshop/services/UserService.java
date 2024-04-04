package se.tronhage.webshop.services;

import org.springframework.stereotype.Service;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.enums.Role;
import se.tronhage.webshop.repository.UserRepo;
import se.tronhage.webshop.exceptions.UserAlreadyExistsException;
import se.tronhage.webshop.exceptions.UserNotFoundException;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User registerNewUser(String firstName, String lastName, String email, String address, String username, String password) {
        if(userRepo.existsByUsername(username)) {
            throw new UserAlreadyExistsException("Username already in use.");
        }
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setAddress(address);
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(Role.user);
        //newUser.setShoppingBasket(new ShoppingBasket()); // ny tom varukorg för användaren
        return userRepo.save(newUser);
    }

    public User updateUserDetails(Long userId, String email, String newPassword) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setEmail(email);
        return userRepo.save(user);
    }

    public boolean authUser(String username, String password){
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            String dbPw = user.get().getPassword();
            return password.equals(dbPw);
        } else {
            return false;
        }
    }
    public Optional<String> authenticateAndRedirect(String username, String password) {
        Optional<User> user = userRepo.findByUsername(username);

        if (user.isPresent() && authUser(username, password)) {
            // antag att authUser-metoden autentiserar användaren som tidigare
            User authenticatedUser = user.get();
            if (Objects.requireNonNull(authenticatedUser.getRole()) == Role.admin) {
                return Optional.of("redirect:/admin.html");
            }
            return Optional.of("redirect:/products");
        }
        return Optional.empty();
    }
}
