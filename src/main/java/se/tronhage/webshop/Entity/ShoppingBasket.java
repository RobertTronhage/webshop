package se.tronhage.webshop.Entity;

import java.util.ArrayList;
import java.util.List;

// Obs! Denna klass är inte en Entity-klass och ska inte mappas till en databastabell
public class ShoppingBasket {
    private List<BasketItem> items = new ArrayList<>();

    public void addItem(BasketItem item) {
        // Implementera logiken för att lägga till en artikel
    }

    public long getTotalPrice() {
        long total = 0;
        for (BasketItem item : items) {
            total += item.getTotalPrice();
        }
        return total; // Totalt pris i ören, cent, etc.
    }

    // Andra metoder
}
