package se.tronhage.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.tronhage.webshop.entity.Order;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.enums.OrderStatus;
import se.tronhage.webshop.enums.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

    List<User> findByRole(Role role);
}
