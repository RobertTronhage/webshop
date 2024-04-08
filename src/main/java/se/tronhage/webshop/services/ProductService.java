package se.tronhage.webshop.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.tronhage.webshop.entity.Product;
import se.tronhage.webshop.exceptions.ProductNotFoundException;
import se.tronhage.webshop.repository.ProductRepo;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public void createNewProduct(String name, String description, int price){
        Product p = new Product();

        p.setName(name);
        p.setDescription(description);
        p.setPrice(price);

        productRepo.save(p);
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
}