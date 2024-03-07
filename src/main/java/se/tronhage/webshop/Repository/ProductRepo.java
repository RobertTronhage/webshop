package se.tronhage.webshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.tronhage.webshop.Entity.Product;

public interface ProductRepo extends JpaRepository<Product,Long> {
}
