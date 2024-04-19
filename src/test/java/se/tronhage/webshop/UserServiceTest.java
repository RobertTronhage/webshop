package se.tronhage.webshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.enums.Role;
import se.tronhage.webshop.exceptions.UserAlreadyExistsException;
import se.tronhage.webshop.repository.UserRepo;
import se.tronhage.webshop.services.EmailService;
import se.tronhage.webshop.services.UserService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest // Starta hela Spring-applikationen
public class UserServiceTest {

    @MockBean
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Test // Testar att en användare skapas korrekt
    public void testRegisterNewUser_CreatesUserSuccessfully() {
        // Antag att användarnamn "newUser" ej existerar
        when(userRepo.existsByUsername("newUser")).thenReturn(false);

        // registerNewUser ska anropa userRepo.save
        userService.registerNewUser("Arne", "Svensson", "arnesvensson@example.com", "Ringvägen 1", "newUser", "password123");

        // Bekräfta att userRepo.save anropades med rätt användardetaljer
        verify(userRepo).save(argThat(user ->
                user.getUsername().equals("newUser") &&
                        user.getEmail().equals("arnesvensson@example.com") &&
                        user.getFirstName().equals("Arne") &&
                        user.getLastName().equals("Svensson") &&
                        user.getAddress().equals("Ringvägen 1") &&
                        user.getPassword().equals("password123") &&
                        user.getRole() == Role.USER
        ));
    }

    @Test // Testar att ett undantag kastas om användarnamnet redan existerar
    public void testRegisterNewUser_ThrowsExceptionIfUsernameExists() {
        when(userRepo.existsByUsername("newUser")).thenReturn(true);

        // Anropa registerNewUser med ett användarnamn som redan existerar
        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.registerNewUser("Arne", "Svensson", "arnesvensson@example.com",
                    "Ringvägen 1", "newUser", "password123");
        });
    }


}
