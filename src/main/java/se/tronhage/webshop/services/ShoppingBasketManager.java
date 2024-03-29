package se.tronhage.webshop.services;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import se.tronhage.webshop.model.BasketItem;
import se.tronhage.webshop.model.ShoppingBasket;

@Service
@SessionScope
public class ShoppingBasketManager {

    private final ShoppingBasket shoppingBasket;

    public ShoppingBasketManager() {
        this.shoppingBasket = new ShoppingBasket();
    }

    public void addItem(BasketItem item) {
        this.shoppingBasket.addItem(item);
    }

    public void removeItem(Long productId, int quantity) {
        this.shoppingBasket.removeItem(productId, quantity);
    }

    public ShoppingBasket getShoppingBasket() {
        return shoppingBasket;
    }

    // Andra hjälpmetoder relaterade till inköpskorgen kan inkluderas här
}

