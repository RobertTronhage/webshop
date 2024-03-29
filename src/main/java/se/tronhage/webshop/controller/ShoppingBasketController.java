package se.tronhage.webshop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/remove-from-basket")
    public String removeItemFromBasket(@RequestParam("productId") Long productId,
                                       @RequestParam("quantity") int quantity, HttpSession session) {
        basketManager.removeItem(productId, quantity);
        return "redirect:/basket";
    }

    @GetMapping("/basket")
    public String showBasket(Model model, HttpSession session) {
        model.addAttribute("basket", basketManager.getShoppingBasket());
        return "basket"; // vy-fil som heter basket.html ?
    }
}

