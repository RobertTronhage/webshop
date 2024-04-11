package se.tronhage.webshop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.tronhage.webshop.model.BasketItem;
import se.tronhage.webshop.model.ShoppingBasket;
import se.tronhage.webshop.services.ShoppingBasketManager;

@Controller
public class ShoppingBasketController {

    private final ShoppingBasketManager basketManager;

    public ShoppingBasketController(ShoppingBasketManager basketManager) {
        this.basketManager = basketManager;
    }

    @PostMapping("/add-to-basket")
    public String addItemToBasket(@RequestParam("productId") Long productId,
                                  @RequestParam("name") String name,
                                  @RequestParam("price") String priceStr,
                                  @RequestParam("quantity") int quantity,
                                  HttpSession session, Model m) {

        ShoppingBasket shoppingbasket = basketManager.getShoppingBasket();

        int price = Integer.parseInt(priceStr);
        basketManager.addItem(productId,name,price,quantity);
        m.addAttribute("shoppingbasket", shoppingbasket);
        return "redirect:/shoppingbasket";
    }

    @PostMapping("/updateQuantity")
    public String updateQuantity(@RequestParam("productId") Long productId,
                                 @RequestParam("quantity") int quantity,
                                 HttpSession session, Model m) {

        ShoppingBasket shoppingbasket = basketManager.getShoppingBasket();

        // Update the quantity of the item in the shopping basket
        basketManager.updateQuantity(productId, quantity);

        m.addAttribute("shoppingbasket", shoppingbasket);
        return "redirect:/shoppingbasket";
    }

    @PostMapping("/remove-from-basket")
    public String removeItemFromBasket(@RequestParam("productId") Long productId,
                                       @RequestParam("quantity") int quantity, HttpSession session, Model m) {
        ShoppingBasket shoppingbasket = basketManager.getShoppingBasket();
        basketManager.removeItem(productId, quantity);
        m.addAttribute("shoppingbasket", shoppingbasket);
        return "redirect:/shoppingbasket";
    }

    @GetMapping("/shoppingbasket")
    public String showBasket(Model m, HttpSession session) {
        m.addAttribute("shoppingbasket", basketManager.getShoppingBasket());
        return "shoppingbasket";
    }
}

