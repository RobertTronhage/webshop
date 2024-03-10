package se.tronhage.webshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.tronhage.webshop.Entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
}
