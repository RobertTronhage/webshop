package se.tronhage.webshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.tronhage.webshop.Entity.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

}
