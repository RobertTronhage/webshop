package se.tronhage.webshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.tronhage.webshop.Entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long>{
}
