package se.tronhage.webshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.tronhage.webshop.Entity.BasketItem;

public interface BasketItemRepo extends JpaRepository<BasketItem, Long> {
}
