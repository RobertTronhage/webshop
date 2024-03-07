package se.tronhage.webshop.Entity;

import jakarta.persistence.*;
import se.tronhage.webshop.Enums.Role;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}
