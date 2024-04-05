package se.tronhage.webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.tronhage.webshop.entity.Product;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.repository.ProductRepo;
import se.tronhage.webshop.services.ProductService;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    ProductRepo productRepo;
    @Autowired
    ProductService productService;

    @Autowired
    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping("/products")
    public String listProducts(Model m){
        List<Product>products = productRepo.findAll();
        m.addAttribute("products",products);
        return "products";
    }

    @GetMapping("/editproduct")
    public String editProduct(@RequestParam("productid") Long productid, Model model){
        Optional<Product> productOptional = productRepo.findById(productid);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            model.addAttribute("product", product);
            return "editproduct";
        } else {
            return "errorPage";
        }
    }

    @PostMapping("/editproduct")
    public String editProduct(@RequestParam("product") Product product, Model m){
        try {
            productService.updateProduct(product);
            m.addAttribute("registrationSuccess","Product updated successfully");
        }catch(Exception e){
            m.addAttribute("errorMessage","Error updating product.");
        }
        return "editproduct";
    }
}