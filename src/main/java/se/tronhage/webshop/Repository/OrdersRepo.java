package se.tronhage.webshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.tronhage.webshop.Entity.Orders;

public interface OrdersRepo extends JpaRepository<Orders, Long>{
}
