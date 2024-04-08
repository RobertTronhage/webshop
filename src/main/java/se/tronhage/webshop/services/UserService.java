package se.tronhage.webshop.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.enums.Role;
import se.tronhage.webshop.repository.UserRepo;
import se.tronhage.webshop.exceptions.UserAlreadyExistsException;
import se.tronhage.webshop.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void registerNewUser(String firstName, String lastName, String email, String address, String username, String password) {
        if (userRepo.existsByUsername(username)) {
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
        userRepo.save(newUser);
    }

    public Optional<User> findById(Long id) {
        System.out.println("findbyid i service");
        return userRepo.findById(id);
    }

    public void updateUser(User updatedUser) {
        Long userId = updatedUser.getId();
        System.out.println(updatedUser.toString());
        if (userId != null) {
            Optional<User> optionalUser = findById(userId);
            System.out.println(userId);
            if (optionalUser.isPresent()) {
                User existingUser = optionalUser.get();

                // Update the existing user's fields with the form data
                existingUser.setFirstName(updatedUser.getFirstName());
                existingUser.setLastName(updatedUser.getLastName());
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setAddress(updatedUser.getAddress());
                existingUser.setUsername(updatedUser.getUsername());
                existingUser.setPassword(updatedUser.getPassword());
                existingUser.setRole(updatedUser.getRole());

                System.out.println("NU SKA DEN JU SOPARAARSd");
                userRepo.save(existingUser);
            }
        }
    }

        public boolean authUser (String username, String password){
            Optional<User> user = userRepo.findByUsername(username);
            if (user.isPresent()) {
                String dbPw = user.get().getPassword();
                return password.equals(dbPw);
            } else {
                return false;
            }
        }

        public Optional<Role> authenticate (String username, String password){
            Optional<User> user = userRepo.findByUsername(username);

            if (user.isPresent() && authUser(username, password)) {
                return Optional.of(user.get().getRole());
            }
            return Optional.empty();
        }

        public List<User> findallRegularUsers () {
            return userRepo.findByRole(Role.USER);
        }

        public List<User> findAllAdmins () {
            return userRepo.findByRole(Role.ADMIN);
        }
    }
