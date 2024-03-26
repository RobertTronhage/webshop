package se.tronhage.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.tronhage.webshop.entity.Orders;

public interface OrdersRepo extends JpaRepository<Orders, Long>{
}
