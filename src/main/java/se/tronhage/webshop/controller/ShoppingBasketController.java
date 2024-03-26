package se.tronhage.webshop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import se.tronhage.webshop.model.BasketItem;
import se.tronhage.webshop.services.ShoppingBasketManager;

@Controller
public class ShoppingBasketController {

    private final ShoppingBasketManager basketManager;

    public ShoppingBasketController(ShoppingBasketManager basketManager) {
        this.basketManager = basketManager;
    }

    @PostMapping("/add-to-basket")
    public String addItemToBasket(@ModelAttribute BasketItem item, HttpSession session) {
        basketManager.addItem(item);
        return "redirect:/basket";
    }

    // Metoder för att ta bort artiklar, visa inköpskorgen, etc.
}

