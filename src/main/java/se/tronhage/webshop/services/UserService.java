package se.tronhage.webshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.tronhage.webshop.entity.Order;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.enums.OrderStatus;
import se.tronhage.webshop.enums.Role;
import se.tronhage.webshop.repository.UserRepo;
import se.tronhage.webshop.exceptions.UserAlreadyExistsException;
import se.tronhage.webshop.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    UserRepo userRepo;

    @Autowired
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
        newUser.setRole(Role.USER);
        //newUser.setShoppingBasket(new ShoppingBasket()); // ny tom varukorg för användaren
        return userRepo.save(newUser);
    }

    @Transactional
    public void updateUser(User updatedUser, String newPassword) {
        // Retrieve the user from the database based on the provided ID
        Optional<User> optionalUser = userRepo.findById(updatedUser.getId());

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Update the user details with the values from the updatedUser object
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setUsername(updatedUser.getUsername());

            // Uppdatera endast lösenordet om ett nytt har angetts
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                existingUser.setPassword(newPassword);
            }
            // Save the updated user back to the database
            userRepo.save(existingUser);
        } else {
            // Handle the case where the user with the provided ID does not exist
            throw new UserNotFoundException("User not found with ID: " + updatedUser.getId());
        }
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
            if (Objects.requireNonNull(authenticatedUser.getRole()) == Role.ADMIN) {
                return Optional.of("redirect:/admin");
            }
            return Optional.of("redirect:/products");
        }
        return Optional.empty();
    }

    public List<User> findallRegularUsers(){
        return userRepo.findByRole(Role.USER);
    }

    public List<User> findAllAdmins(){
        return userRepo.findByRole(Role.ADMIN);
    }
}
