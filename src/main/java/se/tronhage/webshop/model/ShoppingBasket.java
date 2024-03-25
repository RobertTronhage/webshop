package se.tronhage.webshop.model;

import java.util.ArrayList;
import java.util.List;

// Obs! Denna klass är inte en Entity-klass och ska inte mappas till en databastabell
public class ShoppingBasket {
    private List<BasketItem> items = new ArrayList<>();

    public void addItem(BasketItem newItem) {
        for (BasketItem item : items) {
            if (item.getProductId().equals(newItem.getProductId())) {
                // Om produkten redan finns, uppdatera kvantiteten
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }
        // Om produkten inte redan finns, lägg till den nya artikeln i listan
        items.add(newItem);
    }

    public long getTotalPrice() {
        long total = 0;
        for (BasketItem item : items) {
            total += item.getTotalPrice();
        }
        return total; // Totalt pris i ören, cent, etc.
    }

    public void removeItem(Long productId, int quantity) {
    }

    // Andra metoder
}
