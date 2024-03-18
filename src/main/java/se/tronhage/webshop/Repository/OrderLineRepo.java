package se.tronhage.webshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.tronhage.webshop.Entity.OrderLine;

public interface OrderLineRepo extends JpaRepository<OrderLine, Long> {
}
