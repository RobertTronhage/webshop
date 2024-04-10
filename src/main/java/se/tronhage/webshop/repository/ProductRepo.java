package se.tronhage.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.tronhage.webshop.entity.Product;
import se.tronhage.webshop.enums.Category;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    List<Product> findByCategory(Category category);

    List<Product> findByNameContainingIgnoreCase(String query);
}
