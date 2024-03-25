package se.tronhage.webshop.Services;

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
        // Logik för att lägga till en artikel i shoppingBasket
        this.shoppingBasket.addItem(item);
    }

    public void removeItem(Long productId, int quantity) {
        // Logik för att ta bort en artikel från shoppingBasket
        this.shoppingBasket.removeItem(productId, quantity);
    }

    public ShoppingBasket getShoppingBasket() {
        return shoppingBasket;
    }

    // Andra hjälpmetoder relaterade till inköpskorgen kan inkluderas här
}

