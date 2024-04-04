package se.tronhage.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.tronhage.webshop.entity.Order;
import se.tronhage.webshop.enums.OrderStatus;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long>{
    List<Order> findByStatus(OrderStatus status);
}
