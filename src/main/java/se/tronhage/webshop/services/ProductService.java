package se.tronhage.webshop.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.tronhage.webshop.entity.Product;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.enums.Category;
import se.tronhage.webshop.enums.Role;
import se.tronhage.webshop.exceptions.ProductNotFoundException;
import se.tronhage.webshop.repository.ProductRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public void createNewProduct(String name, Category category, String description, int price){
        Product p = new Product();

        p.setName(name);
        p.setCategory(category);
        p.setDescription(description);
        p.setPrice(price);

        productRepo.save(p);
    }

    public List<Product> findAllProducts () {
        return productRepo.findAll();
    }

    public List<Product> findAllPutters () {
        return productRepo.findByCategory(Category.PUTTER);
    }

    public List<Product> findAllMidrange () {
        return productRepo.findByCategory(Category.MIDRANGE);
    }

    public List<Product> findAllDrivers () {
        return productRepo.findByCategory(Category.DISTANCE_DRIVER);
    }

    @Transactional
    public void updateProduct(Product updateProduct) {
        Optional<Product> optionalProduct = productRepo.findById(updateProduct.getId());

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            existingProduct.setName(updateProduct.getName());
            existingProduct.setDescription(updateProduct.getDescription());
            existingProduct.setPrice(updateProduct.getPrice());

            productRepo.save(existingProduct);
        } else {
            throw new ProductNotFoundException("Product not found with ID: " + updateProduct.getId());
        }
    }

    public List<Product> searchProducts(String query) {
        return productRepo.findByNameContainingIgnoreCase(query);
    }
}