package se.tronhage.webshop.Entity;

import java.math.BigDecimal;

// Obs! Denna klass är inte en Entity-klass och ska inte mappas till en databastabell
public class BasketItem {
    private Long productId;
    private int quantity;
    private long unitPrice; // Priset i ören, cent, etc.

    // Konstruktor, getters och setters
    public long getTotalPrice() {
        return unitPrice * quantity;
    }
}
