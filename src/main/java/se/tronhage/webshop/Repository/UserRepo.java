package se.tronhage.webshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.tronhage.webshop.Entity.User;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
