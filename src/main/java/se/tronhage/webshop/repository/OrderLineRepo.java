package se.tronhage.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.tronhage.webshop.entity.OrderLine;

public interface OrderLineRepo extends JpaRepository<OrderLine, Long> {
}
