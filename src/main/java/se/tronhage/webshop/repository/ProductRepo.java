package se.tronhage.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.tronhage.webshop.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
}
