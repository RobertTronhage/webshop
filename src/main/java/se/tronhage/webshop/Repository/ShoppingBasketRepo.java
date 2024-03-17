package se.tronhage.webshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.tronhage.webshop.Entity.ShoppingBasket;

public interface ShoppingBasketRepo extends JpaRepository<ShoppingBasket, Long> {
}
