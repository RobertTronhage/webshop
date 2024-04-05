package se.tronhage.webshop.services;

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

    public void updateProduct(Product updateProduct) {
        // Retrieve the user from the database based on the provided ID
        Optional<Product> optionalProduct = productRepo.findById(updateProduct.getId());

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            existingProduct.setName(updateProduct.getName());
            existingProduct.setDescription(updateProduct.getDescription());
            existingProduct.setPrice(updateProduct.getPrice());

            productRepo.save(existingProduct);
        } else {
            // Handle the case where the user with the provided ID does not exist
            throw new ProductNotFoundException("Product not found with ID: " + updateProduct.getId());
        }
    }
}