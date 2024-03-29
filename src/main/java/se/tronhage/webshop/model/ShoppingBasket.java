package se.tronhage.webshop.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Obs! Denna klass är inte en Entity-klass och ska inte mappas till en databastabell
public class ShoppingBasket {
    private List<BasketItem> items = new ArrayList<>();

    public List<BasketItem> getItems() {
        return items;
    }

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
        return total;
    }

    public void removeItem(Long productId, int quantity) {
        Iterator<BasketItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            BasketItem item = iterator.next();
            if (item.getProductId().equals(productId)) {
                // Minska kvantiteten eller ta bort artikeln om kvantiteten blir 0 eller mindre
                int newQuantity = item.getQuantity() - quantity;
                if (newQuantity > 0) {
                    item.setQuantity(newQuantity);
                } else {
                    iterator.remove();
                }
                break;
            }
        }
    }

    public void clear() {
        this.items.clear(); // Rensar listan med BasketItems
    }
}
