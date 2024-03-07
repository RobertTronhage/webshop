package se.tronhage.webshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.tronhage.webshop.Entity.User;

public interface UserRepo extends JpaRepository<User,Long> {

}
